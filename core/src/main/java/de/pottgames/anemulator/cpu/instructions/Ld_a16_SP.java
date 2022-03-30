package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.MemoryBankController;

public class Ld_a16_SP extends Instruction {

    public Ld_a16_SP(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = this.memory.read16Bit(this.register.getPc());
        this.register.setPc(this.register.getPc() + 2);
        final int sp = this.register.getSp();
        this.memory.write(address, sp & 0xFF);
        this.memory.write(address + 1, sp >>> 8 & 0xFF);

        return 20;
    }


    @Override
    public String toString() {
        return "Ld_a16_SP";
    }

}
