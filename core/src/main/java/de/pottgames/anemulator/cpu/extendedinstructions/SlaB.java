package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class SlaB extends Instruction {

    public SlaB(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int data = this.register.get(RegisterId.B);
        final int msb = data & 0x80;
        data = data << 1 & 0xFF;
        this.register.set(RegisterId.B, data);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, data == 0);
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.H, false);
        this.register.setFlag(FlagId.C, msb != 0);

        return 8;
    }

}
