package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Set0E extends Instruction {

    public Set0E(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int data = this.register.get(RegisterId.E);
        data |= 1 << 0;
        this.register.set(RegisterId.E, data);

        return 8;
    }


    @Override
    public int getLength() {
        return 1;
    }

}
