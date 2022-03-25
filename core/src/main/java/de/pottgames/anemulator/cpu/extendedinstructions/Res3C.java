package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class Res3C extends Instruction {

    public Res3C(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int value = this.register.get(RegisterId.C);
        value &= ~(1 << 3);
        this.register.set(RegisterId.C, value);

        return 8;
    }

}
