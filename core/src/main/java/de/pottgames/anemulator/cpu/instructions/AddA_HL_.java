package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class AddA_HL_ extends Instruction {

    public AddA_HL_(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int value = this.memory.readByte(this.register.get(RegisterId.HL));
        final int a = this.register.get(RegisterId.A);
        final int result = value + a;
        this.register.set(RegisterId.A, result & 0xFF);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, (result & 0xFF) == 0);
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.H, (a & 0xF) + (value & 0xF) > 0xF);
        this.register.setFlag(FlagId.C, result > 0xFF);

        return 8;
    }


    @Override
    public String toString() {
        return "AddA_HL_";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
