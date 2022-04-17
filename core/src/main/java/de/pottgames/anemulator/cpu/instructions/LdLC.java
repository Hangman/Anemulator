package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class LdLC extends Instruction {

    public LdLC(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int value = this.register.get(RegisterId.C);
        this.register.set(RegisterId.L, value);

        return 4;
    }


    @Override
    public String toString() {
        return "LdLC";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
