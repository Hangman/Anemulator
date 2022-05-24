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

package de.pottgames.anemulator.rom;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import de.pottgames.anemulator.error.UnsupportedFeatureException;
import de.pottgames.anemulator.memory.Mbc;
import de.pottgames.anemulator.memory.Mbc1;
import de.pottgames.anemulator.memory.Mbc5;
import de.pottgames.anemulator.memory.RomOnly;

public class RomLoader {

    public static Mbc load(String path) {
        byte[] data = null;
        try {
            data = Files.readAllBytes(Paths.get(path));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        final int[] intData = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            intData[i] = Byte.toUnsignedInt(data[i]);
        }

        Mbc controller = null;
        switch (intData[0x147]) {
            case 0x0:
                // ROM ONLY
                System.out.println("ROM ONLY");
                controller = new RomOnly(intData);
                break;
            case 0x1:
                // ROM + MBC1
                System.out.println("MBC1");
                controller = new Mbc1(intData);
                break;
            // case 0x2:
            // // ROM + MBC1 + RAM
            // // TODO
            // break;
            case 0x3:
                // ROM + MBC1 + RAM + BATTERY
                System.out.println("MBC1 + RAM + BATTERY");
                controller = new Mbc1(intData);
                break;
            // case 0x5:
            // // ROM + MBC2
            // // TODO
            // break;
            case 0x6:
                // ROM + MBC2 + BATTERY
                // // TODO
                System.out.println("MBC2 + BATTERY");
                controller = new Mbc1(intData);
                break;
            // case 0x8:
            // ROM + RAM
            // // TODO
            // break;
            // case 0x9:
            // ROM + RAM + BATTERY
            // // TODO
            // break;
            // case 0xb:
            // ROM + MM01
            // // TODO
            // break;
            // case 0xc:
            // ROM + MM01 + SRAM
            // // TODO
            // break;
            // case 0xd:
            // ROM + MM01 + SRAM + BATTERY
            // // TODO
            // break;
            // case 0xf:
            // ROM + MBC3 + TIMER + BATTERY
            // // TODO
            // break;
            // case 0x10:
            // ROM + MBC3 + TIMER + RAM + BATTERY
            // // TODO
            // break;
            // case 0x11:
            // ROM + MBC3
            // // TODO
            // break;
            // case 0x12:
            // ROM + MBC3 + RAM
            // // TODO
            // break;
            // case 0x13:
            // ROM + MBC3 + RAM + BATTERY
            // // TODO
            // break;
            case 0x19:
                // ROM + MBC5
                System.out.println("ROM + MBC5");
                controller = new Mbc5(intData);
                break;
            case 0x1a:
                // ROM + MBC5 + RAM
                System.out.println("ROM + MBC5 + RAM");
                controller = new Mbc5(intData);
                break;
            case 0x1b:
                // // ROM + MBC5 + RAM + BATTERY
                System.out.println("ROM + MBC5 + RAM + BATTERY");
                controller = new Mbc5(intData);
                break;
            case 0x1c:
                // ROM + MBC5 + RUMBLE
                System.out.println("ROM + MBC5 + RUMBLE");
                controller = new Mbc5(intData);
                break;
            // case 0x1d:
            // ROM + MBC5 + RUMBLE + SRAM
            // // TODO
            // break;
            // case 0x1e:
            // ROM + MBC5 + RUMBLE + SRAM + BATTERY
            // // TODO
            // break;
            // case 0x1f:
            // Pocket Camera
            // // TODO
            // break;
            // case 0xfd:
            // Bandai TAMA5
            // // TODO
            // break;
            // case 0xfe:
            // Hudson HuC-3
            // // TODO
            // break;
            // case 0xff:
            // Hudson HuC-1
            // // TODO
            // break;
            default:
                throw new UnsupportedFeatureException("Unsupported memory controller: " + Integer.toHexString(intData[0x147]));
        }

        return controller;
    }

}
