package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class DecDE extends Instruction {

    public DecDE(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int newValue = this.register.get(RegisterId.DE) - 1 & 0xFFFF;
        this.register.set(RegisterId.DE, newValue);

        return 8;
    }


    @Override
    public String toString() {
        return "DecDE";
    }

}
