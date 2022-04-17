package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class LdCC extends Instruction {

    public LdCC(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int value = this.register.get(RegisterId.C);
        this.register.set(RegisterId.C, value);

        return 4;
    }


    @Override
    public String toString() {
        return "LdCC";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
