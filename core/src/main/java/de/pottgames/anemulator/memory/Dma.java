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

public class Dma implements Memory {
    private final Mmu mmu;
    private int[]     register = new int[1];


    public Dma(Mmu mmu) {
        this.mmu = mmu;
        this.register[0] = 0xFF;
    }


    @Override
    public boolean acceptsAddress(int address) {
        return address == Memory.DMA;
    }


    @Override
    public int readByte(int address) {
        return this.register[address - Memory.DMA];
    }


    @Override
    public void writeByte(int address, int value) {
        this.register[address - Memory.DMA] = value;
        final int dmaAddress = value << 8;
        for (int i = 0; i < 0xA0; i++) {
            this.mmu.writeByte(0xFE00 + i, this.mmu.readByte(dmaAddress + i));
        }
    }

}
