package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.memory.MemoryController;

public class JrNZr8 extends Instruction {

    public JrNZr8(Register register, MemoryController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        if (this.register.isFlagSet(FlagId.Z) == false) {
            final byte offset = (byte) this.memory.read8Bit(this.register.programCounter);
            this.register.programCounter++;
            this.register.programCounter += offset;
            return 12;
        }

        this.register.programCounter++;
        return 8;
    }

}
