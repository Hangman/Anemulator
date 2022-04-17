package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.Memory;

public class IncSP extends Instruction {

    public IncSP(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.setSp(this.register.getSp() + 1 & 0xFFFF);

        return 8;
    }


    @Override
    public String toString() {
        return "IncSP";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
