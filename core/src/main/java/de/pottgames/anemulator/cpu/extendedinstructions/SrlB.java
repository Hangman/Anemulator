package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class SrlB extends Instruction {

    public SrlB(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int value = this.register.get(RegisterId.B);
        final int carry = value & 0b1;
        value = value >>> 1;
        this.register.set(RegisterId.B, value);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, value == 0);
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.H, false);
        this.register.setFlag(FlagId.C, carry > 0 ? true : false);

        return 8;
    }


    @Override
    public int getLength() {
        return 1;
    }

}
