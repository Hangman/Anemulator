package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.Memory;

public class Rst08H extends Instruction {

    public Rst08H(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.setSp(this.register.getSp() - 1);
        this.memory.writeByte(this.register.getSp(), this.register.getPc() >>> 8);
        this.register.setSp(this.register.getSp() - 1);
        this.memory.writeByte(this.register.getSp(), this.register.getPc() & 0xff);

        this.register.setPc(0x08);

        return 16;
    }


    @Override
    public String toString() {
        return "Rst08H";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
