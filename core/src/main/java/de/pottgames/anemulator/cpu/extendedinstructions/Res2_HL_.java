package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Res2_HL_ extends Instruction {

    public Res2_HL_(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = this.register.get(RegisterId.HL);
        int value = this.memory.readByte(address);
        value &= ~(1 << 2);
        this.memory.writeByte(address, value);

        return 16;
    }

}
