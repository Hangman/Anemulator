package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.MemoryController;

public class Rst20H extends Instruction {

    public Rst20H(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.sp--;
        this.memory.write(this.register.sp, this.register.pc >>> 8);
        this.register.sp--;
        this.memory.write(this.register.sp, this.register.pc & 0xff);

        this.register.pc = 0x20;

        return 16;
    }

}
