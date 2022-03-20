package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.MemoryController;

public class LdSPd16 extends Instruction {

    public LdSPd16(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.stackPointer = this.memory.read16Bit(this.register.programCounter);
        this.register.programCounter += 2;
        return 12;
    }

}
