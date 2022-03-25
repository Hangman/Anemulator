package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class PushBC extends Instruction {

    public PushBC(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int data = this.register.get(RegisterId.BC);
        this.register.sp--;
        this.memory.write(this.register.sp, data >>> 8);
        this.register.sp--;
        this.memory.write(this.register.sp, data & 0xFF);

        return 16;
    }

}
