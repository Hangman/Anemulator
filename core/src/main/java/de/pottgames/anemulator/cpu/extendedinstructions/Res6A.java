package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Res6A extends Instruction {

    public Res6A(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int value = this.register.get(RegisterId.A);
        value &= ~(1 << 6);
        this.register.set(RegisterId.A, value);

        return 8;
    }


    @Override
    public int getLength() {
        return 1;
    }

}
