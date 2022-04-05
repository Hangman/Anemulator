package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.memory.Memory;

public class CallNCa16 extends Instruction {

    public CallNCa16(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = this.memory.readWord(this.register.getPc());
        this.register.setPc(this.register.getPc() + 2);
        if (!this.register.isFlagSet(FlagId.C)) {
            this.register.setSp(this.register.getSp() - 1);
            this.memory.writeByte(this.register.getSp(), (this.register.getPc() & 0xFF00) >> 8);
            this.register.setSp(this.register.getSp() - 1);
            this.memory.writeByte(this.register.getSp(), this.register.getPc() & 0xFF);
            this.register.setPc(address);
            return 16;
        }

        return 12;
    }


    @Override
    public String toString() {
        return "CallNCa16";
    }

}
