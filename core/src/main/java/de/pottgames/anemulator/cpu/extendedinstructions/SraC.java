package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class SraC extends Instruction {

    public SraC(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int data = this.register.get(RegisterId.C);
        final int lsb = data & 0x1;
        final int bit7 = data & 0x80;
        data = data >> 1;
        data |= bit7;
        this.register.set(RegisterId.C, data);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, data == 0);
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.H, false);
        this.register.setFlag(FlagId.C, lsb == 1);

        return 8;
    }

}
