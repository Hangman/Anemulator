package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Ld_HL_H extends Instruction {

    public Ld_HL_H(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int value = this.register.get(RegisterId.H);
        this.memory.writeByte(this.register.get(RegisterId.HL), value);

        return 8;
    }


    @Override
    public String toString() {
        return "Ld_HL_H";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
