package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class LdL_HL_ extends Instruction {

    public LdL_HL_(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int value = this.memory.readByte(this.register.get(RegisterId.HL));
        this.register.set(RegisterId.L, value);

        return 8;
    }


    @Override
    public String toString() {
        return "LdL_HL_";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
