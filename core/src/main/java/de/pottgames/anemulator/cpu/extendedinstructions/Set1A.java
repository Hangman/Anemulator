package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Set1A extends Instruction {

    public Set1A(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int data = this.register.get(RegisterId.A);
        data |= 1 << 1;
        this.register.set(RegisterId.A, data);

        return 8;
    }


    @Override
    public int getLength() {
        return 1;
    }

}
