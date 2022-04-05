package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.Memory;

public class Di extends Instruction {

    public Di(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.setInterruptsEnabled(false, false);

        return 4;
    }


    @Override
    public String toString() {
        return "Di";
    }

}
