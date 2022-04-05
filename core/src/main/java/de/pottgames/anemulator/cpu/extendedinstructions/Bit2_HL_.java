package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Bit2_HL_ extends Instruction {

    public Bit2_HL_(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int targetValue = this.memory.readByte(this.register.get(RegisterId.HL));

        // SET FLAGS
        this.register.setFlag(FlagId.Z, (targetValue & 1 << 2) == 0);
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.H, true);

        return 16;
    }

}
