package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class AddHLDE extends Instruction {

    public AddHLDE(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int value = this.register.get(RegisterId.DE);
        final int hl = this.register.get(RegisterId.HL);
        final int result = value + hl;
        this.register.set(RegisterId.HL, result & 0xFFFF);

        // SET FLAGS
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.H, (hl & 0xFFF) + (value & 0xFFF) > 0xFFF);
        this.register.setFlag(FlagId.C, result > 0xFFFF);

        return 8;
    }

}
