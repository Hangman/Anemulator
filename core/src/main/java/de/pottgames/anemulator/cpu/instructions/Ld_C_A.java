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
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.Memory;

public class Ld_C_A extends Instruction {

    public Ld_C_A(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int address = 0xff00 + this.register.get(RegisterId.C);
        this.memory.writeByte(address, this.register.get(RegisterId.A));

        return 8;
    }


    @Override
    public String toString() {
        return "Ld_C_A";
    }


    @Override
    public int getLength() {
        return 2;
    }

}
