package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.MemoryBankController;

public class Ret extends Instruction {

    public Ret(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int data = this.memory.read8Bit(this.register.getSp());
        data = this.memory.read8Bit(this.register.getSp() + 1) << 8 | data;
        this.register.setPc(data);
        this.register.setSp(this.register.getSp() + 2);

        return 16;
    }


    @Override
    public String toString() {
        return "Ret";
    }

}
