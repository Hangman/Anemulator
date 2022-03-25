package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class Ld_a16_A extends Instruction {

    public Ld_a16_A(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = this.memory.read16Bit(this.register.pc);
        this.register.pc += 2;
        final int value = this.register.get(RegisterId.A);
        this.memory.write(address, value);

        return 16;
    }

}
