package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Ld_C_A extends Instruction {

    public Ld_C_A(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = 0xff00 + this.register.get(RegisterId.C);
        this.memory.writeByte(address, this.register.get(RegisterId.A));

        return 8;
    }


    @Override
    public String toString() {
        return "Ld_C_A";
    }

}
