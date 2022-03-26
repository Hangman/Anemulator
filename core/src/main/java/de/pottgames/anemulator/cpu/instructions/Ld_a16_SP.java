package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.MemoryBankController;

public class Ld_a16_SP extends Instruction {

    public Ld_a16_SP(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = this.memory.read16Bit(this.register.pc);
        this.register.pc += 2;
        this.memory.write(address, this.register.sp);

        return 20;
    }

}
