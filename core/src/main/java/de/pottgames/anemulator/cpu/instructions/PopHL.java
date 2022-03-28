package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class PopHL extends Instruction {

    public PopHL(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int data = this.memory.read8Bit(this.register.sp);
        data |= this.memory.read8Bit(this.register.sp + 1) << 8;
        this.register.sp += 2;
        this.register.set(RegisterId.HL, data);

        return 12;
    }

}
