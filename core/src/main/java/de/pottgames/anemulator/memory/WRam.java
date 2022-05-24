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

public class WRam implements Memory {
    private final int[] memory = new int[0xFE00 - 0xC000];


    @Override
    public boolean acceptsAddress(int address) {
        return address >= 0xC000 && address < 0xFE00;
    }


    @Override
    public int readByte(int address) {
        return this.memory[address - 0xC000];
    }


    @Override
    public void writeByte(int address, int value) {
        this.writeInternal(address, value);

        // ECHO
        if (address >= 0xC000 && address <= 0xDDFF) {
            this.writeInternal(address + 0x2000, value);
        } else if (address >= 0xE000) {
            this.writeInternal(address - 0x2000, value);
        }
    }


    private void writeInternal(int address, int value) {
        this.memory[address - 0xC000] = value;
    }

}
