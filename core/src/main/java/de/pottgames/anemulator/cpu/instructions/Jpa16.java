package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.MemoryBankController;

public class Jpa16 extends Instruction {

    public Jpa16(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.setPc(this.memory.read16Bit(this.register.getPc()));
        return 16;
    }


    @Override
    public String toString() {
        return "Jpa16";
    }

}
