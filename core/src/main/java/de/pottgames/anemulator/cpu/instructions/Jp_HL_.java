package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Jp_HL_ extends Instruction {

    public Jp_HL_(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.setPc(this.register.get(RegisterId.HL));

        return 4;
    }


    @Override
    public String toString() {
        return "Jp_HL_";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
