package de.pottgames.anemulator.cpu;

import de.pottgames.anemulator.memory.MemoryBankController;

public class Timer {
    private int                        accumulator;
    private final MemoryBankController memory;


    public Timer(MemoryBankController memory) {
        this.memory = memory;
        this.reset();
    }


    public void reset() {
        this.memory.write(MemoryBankController.TIMA, 0);
    }


    public boolean step() {
        if ((this.memory.read8Bit(MemoryBankController.TAC) & 0b100) > 0) {
            this.accumulator += 4;
            final int requiredClocks = this.requiredClocks();
            if (this.accumulator >= requiredClocks) {
                this.accumulator -= requiredClocks;
                int timer = this.memory.read8Bit(MemoryBankController.TIMA);
                timer++;
                if (timer > 0xFF) {
                    final int timerInitialValue = this.memory.read8Bit(MemoryBankController.TMA);
                    this.memory.write(MemoryBankController.TIMA, timerInitialValue);
                    return true;
                }
                this.memory.write(MemoryBankController.TIMA, timer);
            }
        }

        return false;
    }


    private int requiredClocks() {
        final int tac = this.memory.read8Bit(MemoryBankController.TAC) & 0b11;
        if (tac == 0b01) {
            return 16;
        }
        if (tac == 0b10) {
            return 64;
        }
        if (tac == 0b11) {
            return 256;
        }
        return 1024;
    }

}
