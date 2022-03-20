package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryController;

public class IncD extends Instruction {

    public IncD(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int oldValue = this.register.get(RegisterId.D);
        final int newValue = oldValue + 1;
        this.register.set(RegisterId.D, newValue);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, newValue == 0);
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.H, (oldValue & 0xf) == 0xf);

        return 4;
    }

}
