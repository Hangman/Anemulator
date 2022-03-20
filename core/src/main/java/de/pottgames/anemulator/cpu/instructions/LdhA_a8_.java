package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryController;

public class LdhA_a8_ extends Instruction {

    public LdhA_a8_(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int offset = this.memory.read8Bit(this.register.programCounter);
        this.register.programCounter++;
        this.register.set(RegisterId.A, this.memory.read8Bit(0xff00 + offset));

        return 12;
    }

}
