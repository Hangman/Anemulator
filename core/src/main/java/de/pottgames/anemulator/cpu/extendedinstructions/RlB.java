package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryController;

public class RlB extends Instruction {

    public RlB(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int value = this.register.get(RegisterId.B);
        final int bit7 = value >>> 7 & 0xff;
        final int newValue = (value << 1 & 0xff) + (this.register.isFlagSet(FlagId.C) ? 1 : 0);
        this.register.set(RegisterId.B, newValue);

        this.register.setFlag(FlagId.Z, bit7 == 1);
        this.register.setFlag(FlagId.Z, newValue == 0);
        this.register.setFlag(FlagId.H, false);
        this.register.setFlag(FlagId.N, false);

        return 8;
    }

}
