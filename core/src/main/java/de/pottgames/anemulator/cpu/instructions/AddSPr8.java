package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class AddSPr8 extends Instruction {

    public AddSPr8(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final byte offset = (byte) this.memory.read8Bit(this.register.pc);
        this.register.pc++;
        final int oldSP = this.register.sp;
        this.register.sp += offset;

        // SET FLAGS
        this.register.setFlag(FlagId.Z, false);
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.H, (oldSP & 0xF) + (offset & 0xF) > 0xF);
        this.register.setFlag(FlagId.C, (oldSP & 0xFF) + (offset & 0xFF) > 0xFF);

        return 16;
    }

}
