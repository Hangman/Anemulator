package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryController;

public class PushHL extends Instruction {

    public PushHL(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int data = this.register.get(RegisterId.HL);
        this.register.stackPointer--;
        this.memory.write(this.register.stackPointer, data >>> 8);
        this.register.stackPointer--;
        this.memory.write(this.register.stackPointer, data & 0xff);

        return 16;
    }

}
