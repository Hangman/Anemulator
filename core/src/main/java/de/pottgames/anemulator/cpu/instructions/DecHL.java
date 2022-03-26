package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class DecHL extends Instruction {

    public DecHL(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int newValue = this.register.get(RegisterId.HL) - 1 & 0xFFFF;
        this.register.set(RegisterId.HL, newValue);

        return 8;
    }

}
