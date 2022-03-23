package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.MemoryController;

public class IncSP extends Instruction {

    public IncSP(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.sp = this.register.sp + 1 & 0xffff;

        return 8;
    }

}
