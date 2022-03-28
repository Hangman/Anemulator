package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.MemoryBankController;

public class Rst00H extends Instruction {

    public Rst00H(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.sp--;
        this.memory.write(this.register.sp, this.register.pc >>> 8 & 0xFF);
        this.register.sp--;
        this.memory.write(this.register.sp, this.register.pc & 0xFF);

        this.register.pc = 0x00;

        return 16;
    }

}
