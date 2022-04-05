package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.memory.Memory;

public class JrNZr8 extends Instruction {

    public JrNZr8(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        if (!this.register.isFlagSet(FlagId.Z)) {
            final byte offset = (byte) this.memory.readByte(this.register.getPc());
            this.register.setPc(this.register.getPc() + 1);
            this.register.setPc(this.register.getPc() + offset);
            return 12;
        }

        this.register.setPc(this.register.getPc() + 1);
        return 8;
    }


    @Override
    public String toString() {
        return "JrNZr8";
    }

}
