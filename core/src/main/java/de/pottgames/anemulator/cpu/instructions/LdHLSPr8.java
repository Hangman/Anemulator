package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class LdHLSPr8 extends Instruction {

    public LdHLSPr8(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final byte offset = (byte) this.memory.readByte(this.register.getPc());
        this.register.setPc(this.register.getPc() + 1);
        this.register.set(RegisterId.HL, this.register.getSp() + offset & 0xFFFF);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, false);
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.H, (this.register.getSp() & 0xF) + (offset & 0xF) > 0xF);
        this.register.setFlag(FlagId.C, (this.register.getSp() & 0xFF) + (offset & 0xFF) > 0xFF);

        return 12;
    }


    @Override
    public String toString() {
        return "LdHLSPr8";
    }

}
