package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class LdDL extends Instruction {

    public LdDL(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int value = this.register.get(RegisterId.L);
        this.register.set(RegisterId.D, value);

        return 4;
    }


    @Override
    public String toString() {
        return "LdDL";
    }

}
