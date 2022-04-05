package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class LdA_C_ extends Instruction {

    public LdA_C_(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = 0xFF00 + this.register.get(RegisterId.C);
        this.register.set(RegisterId.A, this.memory.readByte(address));

        return 8;
    }


    @Override
    public String toString() {
        return "LdA_C_";
    }

}
