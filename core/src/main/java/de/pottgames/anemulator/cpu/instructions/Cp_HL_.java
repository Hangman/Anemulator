package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class Cp_HL_ extends Instruction {

    public Cp_HL_(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int a = this.register.get(RegisterId.A);
        final int compareValue = this.memory.read8Bit(this.register.get(RegisterId.HL));

        this.register.setFlag(FlagId.Z, a == compareValue);
        this.register.setFlag(FlagId.N, true);
        this.register.setFlag(FlagId.H, (a & 0xf) < (compareValue & 0xf));
        this.register.setFlag(FlagId.C, a < compareValue);

        return 8;
    }

}
