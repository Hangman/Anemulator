package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Subd8 extends Instruction {

    public Subd8(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int value = this.memory.readByte(this.register.getPc());
        this.register.setPc(this.register.getPc() + 1);
        final int a = this.register.get(RegisterId.A);
        final int result = a - value;
        this.register.set(RegisterId.A, result & 0xFF);

        this.register.setFlag(FlagId.Z, result == 0);
        this.register.setFlag(FlagId.N, true);
        this.register.setFlag(FlagId.H, (a & 0xF) < (value & 0xF));
        this.register.setFlag(FlagId.C, a < value);

        return 8;
    }


    @Override
    public String toString() {
        return "Subd8";
    }


    @Override
    public int getLength() {
        return 2;
    }

}
