package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class Res2A extends Instruction {

    public Res2A(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int value = this.register.get(RegisterId.A);
        value &= ~(1 << 2);
        this.register.set(RegisterId.A, value);

        return 8;
    }

}
