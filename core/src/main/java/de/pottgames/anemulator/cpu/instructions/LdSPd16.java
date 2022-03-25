package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.MemoryBankController;

public class LdSPd16 extends Instruction {

    public LdSPd16(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.sp = this.memory.read16Bit(this.register.pc);
        this.register.pc += 2;
        return 12;
    }

}
