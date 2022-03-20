package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.MemoryController;

public class Calla16 extends Instruction {

    public Calla16(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = this.memory.read16Bit(this.register.programCounter);
        this.register.programCounter += 2;
        this.register.stackPointer--;
        this.memory.write(this.register.stackPointer, this.register.programCounter >>> 8);
        this.register.stackPointer--;
        this.memory.write(this.register.stackPointer, this.register.programCounter & 0xff);
        this.register.programCounter = address;

        return 24;
    }

}
