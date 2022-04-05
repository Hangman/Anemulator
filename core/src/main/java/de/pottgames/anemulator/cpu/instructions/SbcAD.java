package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class SbcAD extends Instruction {

    public SbcAD(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int a = this.register.get(RegisterId.A);
        final int value = this.register.get(RegisterId.D);
        final int carry = this.register.isFlagSet(FlagId.C) ? 1 : 0;
        final int result = a - value - carry;
        this.register.set(RegisterId.A, result & 0xFF);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, (result & 0xFF) == 0);
        this.register.setFlag(FlagId.N, true);
        this.register.setFlag(FlagId.H, (a & 0xF) < (value & 0xF) + carry);
        this.register.setFlag(FlagId.C, a < value + carry);

        return 4;
    }


    @Override
    public String toString() {
        return "SbcAD";
    }

}
