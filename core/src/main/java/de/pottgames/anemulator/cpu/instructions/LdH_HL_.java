package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class LdH_HL_ extends Instruction {

    public LdH_HL_(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = this.register.get(RegisterId.HL);
        final int data = this.memory.readByte(address);
        this.register.set(RegisterId.H, data);

        return 8;
    }


    @Override
    public String toString() {
        return "LdH_HL_";
    }

}
