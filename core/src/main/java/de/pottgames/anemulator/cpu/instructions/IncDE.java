package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class IncDE extends Instruction {

    public IncDE(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int value = this.register.get(RegisterId.DE);
        this.register.set(RegisterId.DE, value + 1 & 0xFFFF);

        return 8;
    }


    @Override
    public String toString() {
        return "IncDE";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
