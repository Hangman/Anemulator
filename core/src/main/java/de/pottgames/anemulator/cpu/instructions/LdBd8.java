package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryController;

public class LdBd8 extends Instruction {

    public LdBd8(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.set(RegisterId.B, this.memory.read8Bit(this.register.programCounter));
        this.register.programCounter++;

        return 8;
    }

}
