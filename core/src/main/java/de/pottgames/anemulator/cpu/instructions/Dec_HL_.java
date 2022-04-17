package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Dec_HL_ extends Instruction {

    public Dec_HL_(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = this.register.get(RegisterId.HL);
        final int newValue = this.memory.readByte(address) - 1;
        this.memory.writeByte(address, newValue & 0xFF);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, newValue == 0);
        this.register.setFlag(FlagId.N, true);
        this.register.setFlag(FlagId.H, (newValue + 1 & 0xF0) != (newValue & 0xF0));

        return 12;
    }


    @Override
    public String toString() {
        return "Dec_HL_";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
