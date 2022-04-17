package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Set0C extends Instruction {

    public Set0C(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int data = this.register.get(RegisterId.C);
        data |= 1 << 0;
        this.register.set(RegisterId.C, data);

        return 8;
    }


    @Override
    public int getLength() {
        return 1;
    }

}
