package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class RrcB extends Instruction {

    public RrcB(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int data = this.register.get(RegisterId.B);
        final int lsb = data & 0x1;
        data = data >> 1;
        data |= lsb << 7;
        this.register.set(RegisterId.B, data);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, data == 0);
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.H, false);
        this.register.setFlag(FlagId.C, lsb == 1);

        return 8;
    }

}
