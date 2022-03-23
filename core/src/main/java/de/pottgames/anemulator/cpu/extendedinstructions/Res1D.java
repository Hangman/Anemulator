package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryController;

public class Res1D extends Instruction {

    public Res1D(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int value = this.register.get(RegisterId.D);
        value &= ~(1 << 1);
        this.register.set(RegisterId.D, value);

        return 8;
    }

}
