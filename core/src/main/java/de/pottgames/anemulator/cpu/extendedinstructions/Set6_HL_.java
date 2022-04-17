package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Set6_HL_ extends Instruction {

    public Set6_HL_(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = this.register.get(RegisterId.HL);
        int data = this.memory.readByte(address);
        data |= 1 << 6;
        this.memory.writeByte(address, data);

        return 16;
    }


    @Override
    public int getLength() {
        return 1;
    }

}
