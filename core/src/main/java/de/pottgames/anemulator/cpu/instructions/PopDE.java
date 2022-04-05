package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class PopDE extends Instruction {

    public PopDE(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int data = this.memory.readByte(this.register.getSp());
        data |= this.memory.readByte(this.register.getSp() + 1) << 8;
        this.register.setSp(this.register.getSp() + 2);
        this.register.set(RegisterId.DE, data);

        return 12;
    }


    @Override
    public String toString() {
        return "PopDE";
    }

}
