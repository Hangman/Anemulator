package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryController;

public class Xord8 extends Instruction {

    public Xord8(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int result = this.register.get(RegisterId.A) ^ this.memory.read8Bit(this.register.pc);
        this.register.pc++;
        this.register.set(RegisterId.A, result);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, result == 0);
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.H, false);
        this.register.setFlag(FlagId.C, false);

        return 8;
    }

}
