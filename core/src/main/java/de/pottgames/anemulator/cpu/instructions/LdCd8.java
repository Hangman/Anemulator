package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class LdCd8 extends Instruction {

    public LdCd8(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.set(RegisterId.C, this.memory.readByte(this.register.getPc()));
        this.register.setPc(this.register.getPc() + 1);

        return 8;
    }


    @Override
    public String toString() {
        return "LdCd8";
    }


    @Override
    public int getLength() {
        return 2;
    }

}
