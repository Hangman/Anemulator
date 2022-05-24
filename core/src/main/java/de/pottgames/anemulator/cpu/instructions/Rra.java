/**
 * Anemulator - A Game Boy emulator<br>
 * Copyright (C) 2022 Matthias Finke
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <a href="https://www.gnu.org/licenses">https://www.gnu.org/licenses<a/>.
 */

package de.pottgames.anemulator.cpu.instructions;

import de.pottgames.anemulator.cpu.Instruction;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.FlagId;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Rra extends Instruction {

    public Rra(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        int a = this.register.get(RegisterId.A);
        final int lsb = a & 0x1;
        final int carry = this.register.isFlagSet(FlagId.C) ? 1 : 0;
        a = a >> 1 | carry << 7;
        this.register.set(RegisterId.A, a);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, false);
        this.register.setFlag(FlagId.H, false);
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.C, lsb != 0);

        return 8;
    }


    @Override
    public String toString() {
        return "Rra";
    }


    @Override
    public int getLength() {
        return 1;
    }

}
