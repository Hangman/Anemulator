package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class DecH extends Instruction {

    public DecH(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int newValue = this.register.get(RegisterId.H) - 1;
        this.register.set(RegisterId.H, newValue & 0xFF);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, newValue == 0);
        this.register.setFlag(FlagId.N, true);
        this.register.setFlag(FlagId.H, (newValue + 1 & 0xF0) != (newValue & 0xF0));

        return 4;
    }


    @Override
    public String toString() {
        return "DecH";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
