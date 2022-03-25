package de.pottgames.anemulator.cpu.extendedinstructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class Res1H extends Instruction {

    public Res1H(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int value = this.register.get(RegisterId.H);
        value &= ~(1 << 1);
        this.register.set(RegisterId.H, value);

        return 8;
    }

}
