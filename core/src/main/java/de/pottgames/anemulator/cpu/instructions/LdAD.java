package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class LdAD extends Instruction {

    public LdAD(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.set(RegisterId.A, this.register.get(RegisterId.D));

        return 4;
    }


    @Override
    public String toString() {
        return "LdAD";
    }

}
