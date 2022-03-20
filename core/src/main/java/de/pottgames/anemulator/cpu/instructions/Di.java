package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.MemoryController;

public class Di extends Instruction {

    public Di(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.interruptsEnabled = false;

        return 4;
    }

}
