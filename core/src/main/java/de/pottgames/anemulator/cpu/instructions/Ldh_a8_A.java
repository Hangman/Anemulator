package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class Ldh_a8_A extends Instruction {

    public Ldh_a8_A(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = 0xFF00 + this.memory.read8Bit(this.register.pc);
        final int value = this.register.get(RegisterId.A);
        this.register.pc++;
        this.memory.write(address, value);
        if (address == 0xFF50 && value == 0x01) {
            this.memory.finishBoot();
        }

        return 12;
    }

}
