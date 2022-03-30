package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class Xor_HL_ extends Instruction {

    public Xor_HL_(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int result = this.register.get(RegisterId.A) ^ this.memory.read8Bit(this.register.get(RegisterId.HL));
        this.register.set(RegisterId.A, result);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, result == 0);
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.H, false);
        this.register.setFlag(FlagId.C, false);

        return 8;
    }


    @Override
    public String toString() {
        return "Xor_HL_";
    }

}
