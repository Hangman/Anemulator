package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class LdA_HLplus_ extends Instruction {

    public LdA_HLplus_(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = this.register.get(RegisterId.HL);
        final int value = this.memory.readByte(address);
        this.register.set(RegisterId.A, value);
        this.register.set(RegisterId.HL, address + 1 & 0xFFFF);

        return 8;
    }


    @Override
    public String toString() {
        return "LdA_HLplus_";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
