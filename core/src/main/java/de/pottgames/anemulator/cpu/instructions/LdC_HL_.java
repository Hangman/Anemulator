package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class LdC_HL_ extends Instruction {

    public LdC_HL_(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int value = this.memory.read8Bit(this.register.get(RegisterId.HL));
        this.register.set(RegisterId.C, value);

        return 4;
    }


    @Override
    public String toString() {
        return "LdC_HL_";
    }

}
