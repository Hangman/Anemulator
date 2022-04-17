package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.memory.Memory;

public class JpNCa16 extends Instruction {

    public JpNCa16(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        if (!this.register.isFlagSet(FlagId.C)) {
            this.register.setPc(this.memory.readWord(this.register.getPc()));
            return 16;
        }

        this.register.setPc(this.register.getPc() + 2);
        return 12;
    }


    @Override
    public String toString() {
        return "JpNCa16";
    }


    @Override
    public int getLength() {
        return 3;
    }

}
