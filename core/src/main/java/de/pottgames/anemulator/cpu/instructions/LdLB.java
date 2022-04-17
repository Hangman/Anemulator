package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class LdLB extends Instruction {

    public LdLB(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.set(RegisterId.L, this.register.get(RegisterId.B));

        return 4;
    }


    @Override
    public String toString() {
        return "LdLB";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
