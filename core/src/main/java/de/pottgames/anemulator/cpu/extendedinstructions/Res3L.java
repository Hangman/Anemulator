package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryController;

public class Res3L extends Instruction {

    public Res3L(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int value = this.register.get(RegisterId.L);
        value &= ~(1 << 3);
        this.register.set(RegisterId.L, value);

        return 8;
    }

}
