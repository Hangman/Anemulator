package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryController;

public class Res5C extends Instruction {

    public Res5C(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int value = this.register.get(RegisterId.C);
        value &= ~(1 << 5);
        this.register.set(RegisterId.C, value);

        return 8;
    }

}
