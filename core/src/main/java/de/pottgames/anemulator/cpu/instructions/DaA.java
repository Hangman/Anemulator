package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class DaA extends Instruction {

    public DaA(Register register, MemoryBankController memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int a = this.register.get(RegisterId.A);
        if (!this.register.isFlagSet(FlagId.N)) {
            if (this.register.isFlagSet(FlagId.H) || (a & 0x0f) > 0x09) {
                a += 0x06;
            }
            if (this.register.isFlagSet(FlagId.C) || a > 0x9f) {
                a += 0x60;
            }
        } else {
            if (this.register.isFlagSet(FlagId.H)) {
                a = a - 0x06 & 0xff;
            }
            if (this.register.isFlagSet(FlagId.C)) {
                a -= 0x60;
            }
        }
        this.register.set(RegisterId.A, a & 0xFF);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, (a & 0xFF) == 0);
        this.register.setFlag(FlagId.H, false);
        this.register.setFlag(FlagId.C, (a & 0x100) == 0x100);

        return 4;
    }

}
