package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Res3B extends Instruction {

    public Res3B(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int value = this.register.get(RegisterId.B);
        value &= ~(1 << 3);
        this.register.set(RegisterId.B, value);

        return 8;
    }

}
