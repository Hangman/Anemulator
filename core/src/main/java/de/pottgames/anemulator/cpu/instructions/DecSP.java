package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.MemoryBankController;

public class DecSP extends Instruction {

    public DecSP(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.setSp(this.register.getSp() - 1);
        this.register.setSp(this.register.getSp() & 0xFFFF);

        return 8;
    }


    @Override
    public String toString() {
        return "DecSP";
    }

}
