package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class LdAC extends Instruction {

    public LdAC(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.set(RegisterId.A, this.register.get(RegisterId.C));

        return 4;
    }


    @Override
    public String toString() {
        return "LdAC";
    }

}
