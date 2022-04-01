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
        int address = this.memory.read8Bit(this.register.getSp());
        address = this.memory.read8Bit(this.register.getSp() + 1) << 8 | address;
        this.register.setSp(this.register.getSp() + 2);
        this.register.setPc(address);
        this.register.setInterruptsEnabled(true, true);

        return 16;
    }


    @Override
    public String toString() {
        return "RetI";
    }

}
