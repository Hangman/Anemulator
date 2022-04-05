package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Rra extends Instruction {

    public Rra(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int a = this.register.get(RegisterId.A);
        final int lsb = a & 0x1;
        final int carry = this.register.isFlagSet(FlagId.C) ? 1 : 0;
        a = a >> 1 | carry << 7;
        this.register.set(RegisterId.A, a);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, false);
        this.register.setFlag(FlagId.H, false);
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.C, lsb != 0);

        return 8;
    }


    @Override
    public String toString() {
        return "Rra";
    }

}
