package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Bit1L extends Instruction {

    public Bit1L(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int targetValue = this.register.get(RegisterId.L);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, (targetValue & 1 << 1) == 0);
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.H, true);

        return 8;
    }

}
