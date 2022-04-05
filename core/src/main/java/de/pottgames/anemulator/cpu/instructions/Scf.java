package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.memory.Memory;

public class Scf extends Instruction {

    public Scf(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.H, false);
        this.register.setFlag(FlagId.C, true);

        return 4;
    }


    @Override
    public String toString() {
        return "Scf";
    }

}
