package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Set6H extends Instruction {

    public Set6H(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int data = this.register.get(RegisterId.H);
        data |= 1 << 6;
        this.register.set(RegisterId.H, data);

        return 8;
    }

}
