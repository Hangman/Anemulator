package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class IncHL extends Instruction {

    public IncHL(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int value = this.register.get(RegisterId.HL);
        this.register.set(RegisterId.HL, value + 1 & 0xFFFF);

        return 8;
    }


    @Override
    public String toString() {
        return "IncHL";
    }

}
