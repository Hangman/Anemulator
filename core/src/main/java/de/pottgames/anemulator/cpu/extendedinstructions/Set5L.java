package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Set5L extends Instruction {

    public Set5L(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int data = this.register.get(RegisterId.L);
        data |= 1 << 5;
        this.register.set(RegisterId.L, data);

        return 8;
    }


    @Override
    public int getLength() {
        return 1;
    }

}
