package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.memory.Memory;

public class AddSPr8 extends Instruction {

    public AddSPr8(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final byte offset = (byte) this.memory.readByte(this.register.getPc());
        this.register.setPc(this.register.getPc() + 1);
        final int oldSP = this.register.getSp();
        this.register.setSp(this.register.getSp() + offset & 0xFFFF);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, false);
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.H, (oldSP & 0xF) + (offset & 0xF) > 0xF);
        this.register.setFlag(FlagId.C, (oldSP & 0xFF) + (offset & 0xFF) > 0xFF);

        return 16;
    }


    @Override
    public String toString() {
        return "AddSPr8";
    }


    @Override
    public int getLength() {
        return 2;
    }

}
