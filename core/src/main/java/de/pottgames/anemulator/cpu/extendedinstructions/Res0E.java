package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Res0E extends Instruction {

    public Res0E(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int value = this.register.get(RegisterId.E);
        value &= ~(1 << 0);
        this.register.set(RegisterId.E, value);

        return 8;
    }

}
