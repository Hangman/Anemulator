package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class RLA extends Instruction {

    public RLA(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int value = this.register.get(RegisterId.A);
        final int bit7 = value >>> 7;
        final int newValue = (value << 1) + (this.register.isFlagSet(FlagId.C) ? 1 : 0) & 0xFF;
        this.register.set(RegisterId.A, newValue);

        this.register.setFlag(FlagId.Z, bit7 == 1);
        this.register.setFlag(FlagId.Z, newValue == 0);
        this.register.setFlag(FlagId.H, false);
        this.register.setFlag(FlagId.N, false);

        return 8;
    }


    @Override
    public String toString() {
        return "RLA";
    }

}
