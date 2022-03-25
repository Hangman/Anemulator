package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class JpCa16 extends Instruction {

    public JpCa16(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        if (this.register.isFlagSet(FlagId.C)) {
            this.register.pc = this.memory.read16Bit(this.register.pc);
            return 16;
        }

        this.register.pc += 2;
        return 12;
    }

}
