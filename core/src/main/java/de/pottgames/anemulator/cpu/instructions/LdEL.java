package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class LdEL extends Instruction {

    public LdEL(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int value = this.register.get(RegisterId.L);
        this.register.set(RegisterId.E, value);

        return 4;
    }


    @Override
    public String toString() {
        return "LdEL";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
