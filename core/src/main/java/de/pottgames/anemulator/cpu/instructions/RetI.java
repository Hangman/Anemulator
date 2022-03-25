package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.MemoryBankController;

public class RetI extends Instruction {

    public RetI(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int address = this.memory.read8Bit(this.register.sp);
        address = this.memory.read8Bit(this.register.sp + 1) << 8 | address;
        this.register.sp += 2;
        this.register.pc = address;
        this.register.setInterruptsEnabled(true);

        return 16;
    }

}
