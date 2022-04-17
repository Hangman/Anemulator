package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.Memory;

public class Ld_a16_SP extends Instruction {

    public Ld_a16_SP(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = this.memory.readWord(this.register.getPc());
        this.register.setPc(this.register.getPc() + 2);
        final int sp = this.register.getSp();
        this.memory.writeByte(address, sp & 0xFF);
        this.memory.writeByte(address + 1, sp >>> 8 & 0xFF);

        return 20;
    }


    @Override
    public String toString() {
        return "Ld_a16_SP";
    }


    @Override
    public int getLength() {
        return 3;
    }

}
