package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.MemoryController;

public class Nop extends Instruction {

    public Nop(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        return 4;
    }

}
