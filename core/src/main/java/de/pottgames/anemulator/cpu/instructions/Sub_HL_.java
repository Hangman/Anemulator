package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class Sub_HL_ extends Instruction {

    public Sub_HL_(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int value = this.memory.read8Bit(this.register.get(RegisterId.HL));
        final int a = this.register.get(RegisterId.A);
        final int result = a - value & 0xFF;
        this.register.set(RegisterId.A, result);

        this.register.setFlag(FlagId.Z, result == 0);
        this.register.setFlag(FlagId.N, true);
        this.register.setFlag(FlagId.H, (a & 0xF) < (value & 0xF));
        this.register.setFlag(FlagId.C, a < value);

        return 8;
    }

}
