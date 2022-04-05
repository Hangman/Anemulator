package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class SrlA extends Instruction {

    public SrlA(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int value = this.register.get(RegisterId.A);
        final int carry = value & 0b1;
        value = value >>> 1;
        this.register.set(RegisterId.A, value);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, value == 0);
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.H, false);
        this.register.setFlag(FlagId.C, carry > 0 ? true : false);

        return 8;
    }

}
