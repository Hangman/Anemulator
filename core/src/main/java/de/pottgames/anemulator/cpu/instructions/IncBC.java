package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class IncBC extends Instruction {

    public IncBC(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int value = this.register.get(RegisterId.BC);
        this.register.set(RegisterId.BC, value + 1 & 0xFFFF);

        return 8;
    }


    @Override
    public String toString() {
        return "IncBC";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
