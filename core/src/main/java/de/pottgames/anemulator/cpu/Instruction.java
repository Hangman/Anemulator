package de.pottgames.anemulator.cpu;

import de.pottgames.anemulator.memory.MemoryBankController;

public abstract class Instruction {
    protected final Register         register;
    protected final MemoryBankController memory;


    public Instruction(Register register, MemoryBankController memory) {
        this.register = register;
        this.memory = memory;
    }


    public abstract int run();

}
