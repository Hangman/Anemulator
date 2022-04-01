package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class Rrca extends Instruction {

    public Rrca(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int a = this.register.get(RegisterId.A);
        final int lsb = a & 0x1;
        a = a >> 1;
        a |= lsb << 7;
        this.register.set(RegisterId.A, a);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, false);
        this.register.setFlag(FlagId.H, false);
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.C, lsb == 0x1);

        return 4;
    }


    @Override
    public String toString() {
        return "Rrca";
    }

}
