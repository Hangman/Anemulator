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

public class Mbc5 implements Mbc {
    private final String  gameName;
    private final int[][] romBanks     = new int[0x200][0x4000];
    private final int[][] ramBanks     = new int[0x10][0x2000];
    private boolean       booted       = false;
    private int           ff50Register = 0xFF;
    private int           romBankNumber;
    private int           ramBankNumber;
    private boolean       ramEnabled;


    public Mbc5(int[] cartridgeData) {
        // COPY ROM BANKS
        int remainingData = cartridgeData.length;
        int bankIndex = 0;
        while (remainingData > 0) {
            final int length = Math.min(remainingData, 0x4000);
            System.arraycopy(cartridgeData, cartridgeData.length - remainingData, this.romBanks[bankIndex], 0, length);
            remainingData -= length;
            bankIndex++;
        }

        // FETCH GAME NAME
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

        if (address >= 0 && address < 0x4000) {
            return this.romBanks[0][address];
        }

        if (address >= 0x4000 && address < 0x8000) {
            return this.romBanks[this.romBankNumber][address - 0x4000];
        }

        if (address >= 0xA000 && address < 0xC000) {
            if (!this.ramEnabled) {
                return 0xFF;
            }
            return this.ramBanks[this.ramBankNumber][address - 0xA000];
        }

        if (address == Memory.DISABLE_BOOT_ROM) {
            return this.ff50Register;
        }

        throw new RuntimeException("Invalid address: " + Integer.toHexString(address));
    }


    @Override
    public void writeByte(int address, int value) {
        if (address >= 0x0000 && address < 0x2000) {
            this.ramEnabled = (value & 0b1111) == 0xA;
            return;
        }

        if (address >= 0x2000 && address < 0x3000) {
            this.romBankNumber &= 0b1_0000_0000;
            this.romBankNumber |= value;
            return;
        }

        if (address >= 0x3000 && address < 0x4000) {
            this.romBankNumber &= 0xFF;
            this.romBankNumber |= (value & 0b1) << 9;
            return;
        }

        if (address >= 0x4000 && address < 0x6000) {
            this.ramBankNumber = value;
            return;
        }

        if (address >= 0xA000 && address < 0xC000) {
            this.ramBanks[this.ramBankNumber][address - 0xA000] = value;
        }
    }


    @Override
    public void setBooted() {
        this.booted = true;
    }


    @Override
    public boolean isBooted() {
        return this.booted;
    }


    @Override
    public String getGameName() {
        return this.gameName;
    }


    @Override
    public String toString() {
        return "MBC5";
    }

}
