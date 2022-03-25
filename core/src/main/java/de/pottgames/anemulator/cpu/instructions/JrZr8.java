package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class JrZr8 extends Instruction {

    public JrZr8(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        if (this.register.isFlagSet(FlagId.Z) == true) {
            final byte offset = (byte) this.memory.read8Bit(this.register.pc);
            this.register.pc++;
            this.register.pc += offset;
            return 12;
        }

        this.register.pc++;
        return 8;
    }

}
