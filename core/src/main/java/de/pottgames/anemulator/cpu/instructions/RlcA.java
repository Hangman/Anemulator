package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class RlcA extends Instruction {

    public RlcA(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int a = this.register.get(RegisterId.A);
        final int msb = (a & 0x80) >> 7;
        a = a << 1;
        a |= msb;
        this.register.set(RegisterId.A, a);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, false);
        this.register.setFlag(FlagId.H, false);
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.C, msb == 0x1);

        return 4;
    }

}
