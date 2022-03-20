package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryController;

public class PopDE extends Instruction {

    public PopDE(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int data = this.memory.read8Bit(this.register.stackPointer);
        data = this.memory.read8Bit(this.register.stackPointer + 1) << 8 | data;
        this.register.stackPointer += 2;
        this.register.set(RegisterId.DE, data);

        return 12;
    }

}
