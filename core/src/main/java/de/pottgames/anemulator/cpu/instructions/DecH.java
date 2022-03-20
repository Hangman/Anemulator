package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryController;

public class DecH extends Instruction {

    public DecH(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int newValue = this.register.get(RegisterId.H) - 1;
        this.register.set(RegisterId.H, newValue & 0xff);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, newValue == 0);
        this.register.setFlag(FlagId.N, true);
        this.register.setFlag(FlagId.H, (newValue + 1 & 0xf0) != (newValue & 0xf0));

        return 4;
    }

}
