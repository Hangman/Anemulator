package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class RlE extends Instruction {

    public RlE(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int value = this.register.get(RegisterId.E);
        final int bit7 = value >>> 7 & 0xff;
        final int newValue = (value << 1) + (this.register.isFlagSet(FlagId.C) ? 1 : 0) & 0xFF;
        this.register.set(RegisterId.E, newValue);

        this.register.setFlag(FlagId.C, bit7 == 1);
        this.register.setFlag(FlagId.Z, newValue == 0);
        this.register.setFlag(FlagId.H, false);
        this.register.setFlag(FlagId.N, false);

        return 8;
    }


    @Override
    public int getLength() {
        return 1;
    }

}
