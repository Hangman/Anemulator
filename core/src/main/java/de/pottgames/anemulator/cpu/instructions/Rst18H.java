package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.MemoryController;

public class Rst18H extends Instruction {

    public Rst18H(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.stackPointer--;
        this.memory.write(this.register.stackPointer, this.register.programCounter >>> 8);
        this.register.stackPointer--;
        this.memory.write(this.register.stackPointer, this.register.programCounter & 0xff);

        this.register.programCounter = 0x18;

        return 16;
    }

}
