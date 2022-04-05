package de.pottgames.anemulator.cpu;

import de.pottgames.anemulator.memory.Memory;

public abstract class Instruction {
    protected final Register register;
    protected final Memory   memory;


    public Instruction(Register register, Memory memory) {
        this.register = register;
        this.memory = memory;
    }


    public abstract int run();

}
