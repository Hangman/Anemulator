package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.memory.Memory;

public class Ccf extends Instruction {

    public Ccf(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.H, false);
        this.register.setFlag(FlagId.C, !this.register.isFlagSet(FlagId.C));

        return 4;
    }


    @Override
    public String toString() {
        return "Ccf";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
