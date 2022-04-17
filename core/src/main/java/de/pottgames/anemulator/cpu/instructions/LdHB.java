package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class LdHB extends Instruction {

    public LdHB(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int value = this.register.get(RegisterId.B);
        this.register.set(RegisterId.H, value);

        return 4;
    }


    @Override
    public String toString() {
        return "LdHB";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
