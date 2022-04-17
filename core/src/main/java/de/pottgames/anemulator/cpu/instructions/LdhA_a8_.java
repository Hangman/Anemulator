package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class LdhA_a8_ extends Instruction {

    public LdhA_a8_(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int offset = this.memory.readByte(this.register.getPc());
        this.register.setPc(this.register.getPc() + 1);
        final int value = this.memory.readByte(0xff00 + offset);
        this.register.set(RegisterId.A, value);

        return 12;
    }


    @Override
    public String toString() {
        return "LdhA_a8_";
    }


    @Override
    public int getLength() {
        return 2;
    }

}
