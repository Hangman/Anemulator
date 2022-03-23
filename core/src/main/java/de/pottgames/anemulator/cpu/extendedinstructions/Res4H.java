package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryController;

public class Res4H extends Instruction {

    public Res4H(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int value = this.register.get(RegisterId.H);
        value &= ~(1 << 4);
        this.register.set(RegisterId.H, value);

        return 8;
    }

}
