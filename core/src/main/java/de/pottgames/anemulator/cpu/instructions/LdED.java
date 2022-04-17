package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class LdED extends Instruction {

    public LdED(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int value = this.register.get(RegisterId.D);
        this.register.set(RegisterId.E, value);

        return 4;
    }


    @Override
    public String toString() {
        return "LdED";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
