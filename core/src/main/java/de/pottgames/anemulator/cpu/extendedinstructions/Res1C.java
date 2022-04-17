package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Res1C extends Instruction {

    public Res1C(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int value = this.register.get(RegisterId.C);
        value &= ~(1 << 1);
        this.register.set(RegisterId.C, value);

        return 8;
    }


    @Override
    public int getLength() {
        return 1;
    }

}
