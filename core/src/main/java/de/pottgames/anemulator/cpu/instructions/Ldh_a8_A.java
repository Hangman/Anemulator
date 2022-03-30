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
        final int address = 0xFF00 + this.memory.read8Bit(this.register.getPc());
        final int value = this.register.get(RegisterId.A);
        this.register.setPc(this.register.getPc() + 1);
        this.memory.write(address, value);

        return 12;
    }


    @Override
    public String toString() {
        return "Ldh_a8_A";
    }

}
