package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.MemoryController;

public class Jpa16 extends Instruction {

    public Jpa16(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.programCounter = this.memory.read16Bit(this.register.programCounter);
        return 16;
    }

}
