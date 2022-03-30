package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class CplA extends Instruction {

    public CplA(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int a = this.register.get(RegisterId.A);
        this.register.set(RegisterId.A, ~a & 0xFF);

        // SET FLAGS
        this.register.setFlag(FlagId.N, true);
        this.register.setFlag(FlagId.H, true);

        return 4;
    }


    @Override
    public String toString() {
        return "CplA";
    }

}
