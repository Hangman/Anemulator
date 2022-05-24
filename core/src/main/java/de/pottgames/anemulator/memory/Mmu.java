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

package de.pottgames.anemulator.memory;

import java.util.List;

public class Mmu implements Memory {
    private Memory[] unitLut = new Memory[0xFFFF + 1];


    public void addMemoryUnits(List<Memory> unitList) {
        for (int i = 0; i < this.unitLut.length; i++) {
            for (final Memory unit : unitList) {
                if (unit.acceptsAddress(i)) {
                    this.unitLut[i] = unit;
                    break;
                }
            }
        }
    }


    @Override
    public boolean acceptsAddress(int address) {
        return this.unitLut[address] != null;
    }


    @Override
    public int readByte(int address) {
        final Memory memory = this.unitLut[address];
        return memory.readByte(address);
    }


    @Override
    public void writeByte(int address, int value) {
        final Memory memory = this.unitLut[address];
        memory.writeByte(address, value);
    }

}
