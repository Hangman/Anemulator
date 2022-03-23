package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryController;

public class LdA_HLplus_ extends Instruction {

    public LdA_HLplus_(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = this.register.get(RegisterId.HL);
        final int value = this.memory.read8Bit(address);
        this.register.set(RegisterId.A, value);
        this.register.set(RegisterId.HL, address + 1 & 0xFFFF);

        return 8;
    }

}
