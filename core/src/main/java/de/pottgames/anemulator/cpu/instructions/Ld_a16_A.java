package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryController;

public class Ld_a16_A extends Instruction {

    public Ld_a16_A(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = this.memory.read16Bit(this.register.programCounter);
        this.register.programCounter += 2;
        final int value = this.register.get(RegisterId.A);
        this.memory.write(address, value);

        return 16;
    }

}
