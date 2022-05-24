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

public class InterruptRegisters implements Memory {
    private final int[] memory = new int[2];


    @Override
    public boolean acceptsAddress(int address) {
        return address == Memory.IF || address == Memory.IE;
    }


    @Override
    public int readByte(int address) {
        switch (address) {
            case Memory.IF:
                return this.memory[0];
            case Memory.IE:
                return this.memory[1];
            default:
                throw new RuntimeException("Invalid address.");
        }
    }


    @Override
    public void writeByte(int address, int value) {
        switch (address) {
            case Memory.IF:
                this.memory[0] = value | 0xE0;
                break;
            case Memory.IE:
                this.memory[1] = value;
                break;
            default:
                throw new RuntimeException("Invalid address.");
        }
    }

}
