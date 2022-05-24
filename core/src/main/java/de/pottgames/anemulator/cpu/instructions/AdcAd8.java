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

public class AdcAd8 extends Instruction {

    public AdcAd8(Register register, Memory memory) {
        super(register, memory);
    }


    @Override
    public int run() {
        final int a = this.register.get(RegisterId.A);
        final int value = this.memory.readByte(this.register.getPc());
        this.register.setPc(this.register.getPc() + 1);
        final int carryBit = this.register.isFlagSet(FlagId.C) ? 1 : 0;
        final int result = value + a + carryBit & 0xFF;
        this.register.set(RegisterId.A, result);

        // SET FLAGS
        this.register.setFlag(FlagId.Z, result == 0);
        this.register.setFlag(FlagId.N, false);
        this.register.setFlag(FlagId.H, (a & 0xF) + (value & 0xF) + carryBit > 0xF);
        this.register.setFlag(FlagId.C, value + a + carryBit > 0xFF);

        return 8;
    }


    @Override
    public String toString() {
        return "AdcAd8";
    }


    @Override
    public int getLength() {
        return 2;
    }

}
