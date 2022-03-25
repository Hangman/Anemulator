package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class RetNZ extends Instruction {

    public RetNZ(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        if (!this.register.isFlagSet(FlagId.Z)) {
            int returnAddress = this.memory.read8Bit(this.register.sp);
            returnAddress = this.memory.read8Bit(this.register.sp + 1) << 8 | returnAddress;
            this.register.sp += 2;
            this.register.pc = returnAddress;
            return 20;
        }

        return 8;
    }

}
