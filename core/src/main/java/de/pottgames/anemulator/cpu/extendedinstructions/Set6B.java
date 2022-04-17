package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Set6B extends Instruction {

    public Set6B(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int data = this.register.get(RegisterId.B);
        data |= 1 << 6;
        this.register.set(RegisterId.B, data);

        return 8;
    }


    @Override
    public int getLength() {
        return 1;
    }

}
