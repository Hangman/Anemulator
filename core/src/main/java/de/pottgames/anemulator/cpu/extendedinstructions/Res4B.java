package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Res4B extends Instruction {

    public Res4B(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int value = this.register.get(RegisterId.B);
        value &= ~(1 << 4);
        this.register.set(RegisterId.B, value);

        return 8;
    }


    @Override
    public int getLength() {
        return 1;
    }

}
