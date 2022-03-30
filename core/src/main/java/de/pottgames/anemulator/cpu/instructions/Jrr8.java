package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.MemoryBankController;

public class Jrr8 extends Instruction {

    public Jrr8(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final byte offset = (byte) this.memory.read8Bit(this.register.getPc());
        this.register.setPc(this.register.getPc() + 1);
        this.register.setPc(this.register.getPc() + offset);

        return 12;
    }


    @Override
    public String toString() {
        return "Jrr8";
    }

}
