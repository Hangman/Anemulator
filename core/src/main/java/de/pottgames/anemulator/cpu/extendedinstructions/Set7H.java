package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Set7H extends Instruction {

    public Set7H(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int data = this.register.get(RegisterId.H);
        data |= 1 << 7;
        this.register.set(RegisterId.H, data);

        return 8;
    }


    @Override
    public int getLength() {
        return 1;
    }

}
