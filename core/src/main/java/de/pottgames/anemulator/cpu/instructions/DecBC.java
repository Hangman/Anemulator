package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class DecBC extends Instruction {

    public DecBC(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int newValue = this.register.get(RegisterId.BC) - 1 & 0xFFFF;
        this.register.set(RegisterId.BC, newValue);

        return 8;
    }


    @Override
    public String toString() {
        return "DecBC";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
