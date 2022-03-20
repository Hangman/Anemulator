package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryController;

public class XorA extends Instruction {

    public XorA(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.resetFlags();
        final int result = this.register.get(RegisterId.A) ^ this.register.get(RegisterId.A);
        this.register.set(RegisterId.A, result);
        this.register.setFlag(FlagId.Z, result == 0);

        return 4;
    }

}
