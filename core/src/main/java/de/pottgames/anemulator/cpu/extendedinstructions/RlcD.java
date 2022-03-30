package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class RlcD extends Instruction {

    public RlcD(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int data = this.register.get(RegisterId.D);
        final int msb = (data & 0x80) >> 7;
        data = data << 1;
        data |= msb;
        data &= 0xFF;
        this.register.set(RegisterId.D, data);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, data == 0);
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.H, false);
        this.register.setFlag(FlagId.C, msb == 1);

        return 8;
    }

}
