package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryController;

public class Ldh_a8_A extends Instruction {

    public Ldh_a8_A(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int offset = this.memory.read8Bit(this.register.programCounter);
        this.register.programCounter++;
        this.memory.write(0xff00 + offset, this.register.get(RegisterId.A));
        return 12;
    }

}
