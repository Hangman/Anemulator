package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Res6D extends Instruction {

    public Res6D(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int value = this.register.get(RegisterId.D);
        value &= ~(1 << 6);
        this.register.set(RegisterId.D, value);

        return 8;
    }


    @Override
    public int getLength() {
        return 1;
    }

}
