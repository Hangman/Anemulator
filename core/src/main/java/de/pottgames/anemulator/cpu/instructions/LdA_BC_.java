package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class LdA_BC_ extends Instruction {

    public LdA_BC_(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = this.register.get(RegisterId.BC);
        this.register.set(RegisterId.A, this.memory.readByte(address));

        return 8;
    }


    @Override
    public String toString() {
        return "LdA_BC_";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
