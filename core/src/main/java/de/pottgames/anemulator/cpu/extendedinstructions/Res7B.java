package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Res7B extends Instruction {

    public Res7B(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int value = this.register.get(RegisterId.B);
        value &= ~(1 << 7);
        this.register.set(RegisterId.B, value);

        return 8;
    }


    @Override
    public int getLength() {
        return 1;
    }

}
