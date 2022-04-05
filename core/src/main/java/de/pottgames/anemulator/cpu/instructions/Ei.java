package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.Memory;

public class Ei extends Instruction {

    public Ei(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.setInterruptsEnabled(true, false);

        return 4;
    }


    @Override
    public String toString() {
        return "Ei";
    }

}
