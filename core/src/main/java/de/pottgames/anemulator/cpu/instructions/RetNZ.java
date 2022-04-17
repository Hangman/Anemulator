package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.memory.Memory;

public class RetNZ extends Instruction {

    public RetNZ(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        if (!this.register.isFlagSet(FlagId.Z)) {
            int returnAddress = this.memory.readByte(this.register.getSp());
            returnAddress = this.memory.readByte(this.register.getSp() + 1) << 8 | returnAddress;
            this.register.setSp(this.register.getSp() + 2);
            this.register.setPc(returnAddress);
            return 20;
        }

        return 8;
    }


    @Override
    public String toString() {
        return "RetNZ";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
