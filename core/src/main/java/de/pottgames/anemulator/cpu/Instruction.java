package de.pottgames.anemulator.cpu;

import de.pottgames.anemulator.memory.MemoryController;

public abstract class Instruction {
    protected final Register         register;
    protected final MemoryController memory;


    public Instruction(Register register, MemoryController memory) {
        this.register = register;
        this.memory = memory;
    }


    public abstract int run();

}
