package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Bit0B extends Instruction {

    public Bit0B(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int targetValue = this.register.get(RegisterId.B);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, (targetValue & 0b1) == 0);
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.H, true);

        return 8;
    }

}
