package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.Memory;

public class DecSP extends Instruction {

    public DecSP(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.setSp(this.register.getSp() - 1 & 0xFFFF);

        return 8;
    }


    @Override
    public String toString() {
        return "DecSP";
    }

}
