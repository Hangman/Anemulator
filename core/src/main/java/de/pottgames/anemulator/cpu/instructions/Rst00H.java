package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.MemoryBankController;

public class Rst00H extends Instruction {

    public Rst00H(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        this.register.setSp(this.register.getSp() - 1);
        this.memory.write(this.register.getSp(), this.register.getPc() >>> 8 & 0xFF);
        this.register.setSp(this.register.getSp() - 1);
        this.memory.write(this.register.getSp(), this.register.getPc() & 0xFF);

        this.register.setPc(0x00);

        return 16;
    }


    @Override
    public String toString() {
        return "Rst00H";
    }

}
