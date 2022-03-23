package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryController;

public class LdAH extends Instruction {

    public LdAH(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int h = this.register.get(RegisterId.H);
        this.register.set(RegisterId.A, h);

        return 4;
    }

}
