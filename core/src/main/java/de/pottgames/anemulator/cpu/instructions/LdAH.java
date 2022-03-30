package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class LdAH extends Instruction {

    public LdAH(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int h = this.register.get(RegisterId.H);
        this.register.set(RegisterId.A, h);

        return 4;
    }


    @Override
    public String toString() {
        return "LdAH";
    }

}
