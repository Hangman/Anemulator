package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Set5D extends Instruction {

    public Set5D(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int data = this.register.get(RegisterId.D);
        data |= 1 << 5;
        this.register.set(RegisterId.D, data);

        return 8;
    }


    @Override
    public int getLength() {
        return 1;
    }

}
