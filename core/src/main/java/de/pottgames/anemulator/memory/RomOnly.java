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

public class RomOnly implements Mbc {
    private final int[]  rom         = new int[0x8001];
    private final int[]  externalRam = new int[0xC000 - 0xA000];
    private boolean      booted      = false;
    private final String gameName;


    public RomOnly(int[] romData) {
        if (romData.length > 0x8000) {
            throw new RuntimeException("ROM size too large for a RomOnly cartridge.");
        }

        System.arraycopy(romData, 0, this.rom, 0, Math.min(0x8000, romData.length));
        final char[] gameNameChars = new char[0x143 - 0x134];
        for (int i = 0; i < gameNameChars.length; i++) {
            gameNameChars[i] = (char) this.readByte(0x134 + i);
        }
        this.gameName = new String(gameNameChars).trim();
    }


    @Override
    public boolean acceptsAddress(int address) {
        return address >= 0 && address < 0x8000 || address == Memory.DISABLE_BOOT_ROM || address >= 0xA000 && address < 0xC000;
    }


    @Override
    public int readByte(int address) {
        if (!this.booted && address >= 0x0000 && address <= 0x00FF) {
            return Mbc.BOOT_ROM[address];
        }

        if (address >= 0xA000 && address < 0xC000) {
            return this.externalRam[address - 0xA000];
        }

        if (address == Memory.DISABLE_BOOT_ROM) {
            return this.rom[0x8000];
        }

        return this.rom[address];
    }


    @Override
    public void writeByte(int address, int value) {
        if (address == Memory.DISABLE_BOOT_ROM) {
            if (value > 0) {
                this.booted = true;
            }
            this.rom[0x8000] = value;
        }

        if (address >= 0xA000 && address < 0xC000) {
            this.externalRam[address - 0xA000] = value;
        }

        // ignore writes to the ROM
    }


    @Override
    public String getGameName() {
        return this.gameName;
    }


    @Override
    public void setBooted() {
        this.booted = true;
    }


    @Override
    public boolean isBooted() {
        return this.booted;
    }

}
