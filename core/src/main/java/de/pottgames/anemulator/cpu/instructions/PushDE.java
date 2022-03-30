package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class PushDE extends Instruction {

    public PushDE(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int data = this.register.get(RegisterId.DE);
        this.register.setSp(this.register.getSp() - 1);
        this.memory.write(this.register.getSp(), data >>> 8);
        this.register.setSp(this.register.getSp() - 1);
        this.memory.write(this.register.getSp(), data & 0xFF);

        return 16;
    }


    @Override
    public String toString() {
        return "PushDE";
    }

}
