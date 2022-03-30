package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class RetNC extends Instruction {

    public RetNC(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        if (!this.register.isFlagSet(FlagId.C)) {
            int returnAddress = this.memory.read8Bit(this.register.getSp());
            returnAddress = this.memory.read8Bit(this.register.getSp() + 1) << 8 | returnAddress;
            this.register.setSp(this.register.getSp() + 2);
            this.register.setPc(returnAddress);
            return 20;
        }

        return 8;
    }


    @Override
    public String toString() {
        return "RetNC";
    }

}
