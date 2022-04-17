package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.Memory;

public class Nop extends Instruction {

    public Nop(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        return 4;
    }


    @Override
    public String toString() {
        return "Nop";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
