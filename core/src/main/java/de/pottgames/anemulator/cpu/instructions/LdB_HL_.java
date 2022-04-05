package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class LdB_HL_ extends Instruction {

    public LdB_HL_(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int value = this.memory.readByte(this.register.get(RegisterId.HL));
        this.register.set(RegisterId.B, value);

        return 8;
    }


    @Override
    public String toString() {
        return "LdB_HL_";
    }

}
