package de.pottgames.anemulator.cpu;

import de.pottgames.anemulator.memory.MemoryBankController;

public class DividerTimer {
    private static final int           CLOCKS_PER_DIVIDER_INC = 256;
    private final MemoryBankController memory;
    private int                        accumulator;


    public DividerTimer(MemoryBankController memory) {
        this.memory = memory;
    }


    public void step() {
        this.accumulator += 4;
        if (this.accumulator >= DividerTimer.CLOCKS_PER_DIVIDER_INC) {
            this.accumulator -= DividerTimer.CLOCKS_PER_DIVIDER_INC;
            this.memory.incDiv();
        }
    }

}
