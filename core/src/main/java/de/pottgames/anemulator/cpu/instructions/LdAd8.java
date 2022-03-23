package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryController;

public class LdAd8 extends Instruction {

    public LdAd8(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.set(RegisterId.A, this.memory.read8Bit(this.register.pc));
        this.register.pc++;

        return 8;
    }

}
