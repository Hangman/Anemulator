package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.MemoryController;

public class Jrr8 extends Instruction {

    public Jrr8(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final byte offset = (byte) this.memory.read8Bit(this.register.programCounter);
        this.register.programCounter++;
        this.register.programCounter += offset;

        return 12;
    }

}
