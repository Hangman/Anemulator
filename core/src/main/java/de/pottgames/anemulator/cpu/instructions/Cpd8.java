package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Cpd8 extends Instruction {

    public Cpd8(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int a = this.register.get(RegisterId.A);
        final int compareValue = this.memory.readByte(this.register.getPc());
        this.register.setPc(this.register.getPc() + 1);

        this.register.setFlag(FlagId.Z, a == compareValue);
        this.register.setFlag(FlagId.N, true);
        this.register.setFlag(FlagId.H, (a & 0xf) < (compareValue & 0xf));
        this.register.setFlag(FlagId.C, a < compareValue);

        return 8;
    }


    @Override
    public String toString() {
        return "Cpd8";
    }

}
