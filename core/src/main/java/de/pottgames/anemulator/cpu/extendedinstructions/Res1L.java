package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class Res1L extends Instruction {

    public Res1L(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int value = this.register.get(RegisterId.L);
        value &= ~(1 << 1);
        this.register.set(RegisterId.L, value);

        return 8;
    }

}
