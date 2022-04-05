package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class LdBH extends Instruction {

    public LdBH(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int value = this.register.get(RegisterId.H);
        this.register.set(RegisterId.B, value);

        return 4;
    }


    @Override
    public String toString() {
        return "LdBH";
    }

}
