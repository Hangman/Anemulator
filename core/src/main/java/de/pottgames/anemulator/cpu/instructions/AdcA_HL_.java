package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class AdcA_HL_ extends Instruction {

    public AdcA_HL_(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int a = this.register.get(RegisterId.A);
        final int value = this.memory.read8Bit(this.register.get(RegisterId.HL));
        final int carryBit = this.register.isFlagSet(FlagId.C) ? 1 : 0;
        final int result = value + a + carryBit & 0xFF;
        this.register.set(RegisterId.A, result);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, result == 0);
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.H, (a & 0xF) + (value & 0xF) + carryBit > 0xF);
        this.register.setFlag(FlagId.C, value + a + carryBit > 0xFF);

        return 8;
    }


    @Override
    public String toString() {
        return "AdcA_HL_";
    }

}
