package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class CallNZa16 extends Instruction {

    public CallNZa16(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = this.memory.read16Bit(this.register.pc);
        this.register.pc += 2;
        if (!this.register.isFlagSet(FlagId.Z)) {
            this.register.sp--;
            this.memory.write(this.register.sp, (this.register.pc & 0xFF00) >> 8);
            this.register.sp--;
            this.memory.write(this.register.sp, this.register.pc & 0xFF);
            this.register.pc = address;
            return 16;
        }

        return 12;
    }

}
