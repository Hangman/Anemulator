package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.Memory;

public class Ret extends Instruction {

    public Ret(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int data = this.memory.readByte(this.register.getSp());
        data = this.memory.readByte(this.register.getSp() + 1) << 8 | data;
        this.register.setPc(data);
        this.register.setSp(this.register.getSp() + 2);

        return 16;
    }


    @Override
    public String toString() {
        return "Ret";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
