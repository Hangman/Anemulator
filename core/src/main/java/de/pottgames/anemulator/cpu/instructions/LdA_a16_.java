package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class LdA_a16_ extends Instruction {

    public LdA_a16_(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = this.memory.read16Bit(this.register.getPc());
        this.register.setPc(this.register.getPc() + 2);
        final int value = this.memory.read8Bit(address);
        this.register.set(RegisterId.A, value);

        return 16;
    }


    @Override
    public String toString() {
        return "LdA_a16_";
    }

}
