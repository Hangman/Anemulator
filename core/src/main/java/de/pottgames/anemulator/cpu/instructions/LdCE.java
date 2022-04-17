package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class LdCE extends Instruction {

    public LdCE(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int value = this.register.get(RegisterId.E);
        this.register.set(RegisterId.C, value);

        return 4;
    }


    @Override
    public String toString() {
        return "LdCE";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
