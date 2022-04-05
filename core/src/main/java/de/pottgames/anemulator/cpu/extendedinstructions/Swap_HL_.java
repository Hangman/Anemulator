package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Swap_HL_ extends Instruction {

    public Swap_HL_(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = this.register.get(RegisterId.HL);
        final int value = this.memory.readByte(address) & 0xFF;
        final int lowNibble = value & 0x0F;
        final int highNibble = value >>> 4;
        final int newValue = highNibble | lowNibble << 4;
        this.memory.writeByte(address, newValue);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, newValue == 0);
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.H, false);
        this.register.setFlag(FlagId.C, false);

        return 16;
    }

}
