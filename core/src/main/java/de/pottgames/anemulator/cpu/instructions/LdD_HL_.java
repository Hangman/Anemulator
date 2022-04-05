package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class LdD_HL_ extends Instruction {

    public LdD_HL_(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = this.register.get(RegisterId.HL);
        this.register.set(RegisterId.D, this.memory.readByte(address));

        return 8;
    }


    @Override
    public String toString() {
        return "LdD_HL_";
    }

}
