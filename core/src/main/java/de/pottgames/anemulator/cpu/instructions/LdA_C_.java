package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class LdA_C_ extends Instruction {

    public LdA_C_(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = 0xFF00 + this.register.get(RegisterId.C);
        this.register.set(RegisterId.A, this.memory.read8Bit(address));

        return 8;
    }

}
