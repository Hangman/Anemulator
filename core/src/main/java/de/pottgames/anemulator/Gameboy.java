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

package de.pottgames.anemulator;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Pixmap;

import de.pottgames.anemulator.apu.Apu;
import de.pottgames.anemulator.cpu.Booter;
import de.pottgames.anemulator.cpu.Cpu;
import de.pottgames.anemulator.cpu.Timer;
import de.pottgames.anemulator.input.Joypad;
import de.pottgames.anemulator.memory.Dma;
import de.pottgames.anemulator.memory.InterruptRegisters;
import de.pottgames.anemulator.memory.Mbc;
import de.pottgames.anemulator.memory.Memory;
import de.pottgames.anemulator.memory.Mmu;
import de.pottgames.anemulator.memory.RandomAccessMemory;
import de.pottgames.anemulator.memory.SerialBus;
import de.pottgames.anemulator.memory.WRam;
import de.pottgames.anemulator.ppu.Ppu;
import de.pottgames.anemulator.rom.RomLoader;

public class Gameboy {
    private final Cpu          cpu;
    private final Ppu          ppu;
    private final Apu          apu;
    private final Mbc          mbc;
    private final Mmu          mmu;
    private final Timer        timer;
    private final Joypad       joypad;
    private final List<Memory> memoryList = new ArrayList<>();


    public Gameboy(String cartridgePath, Pixmap screen) {
        // CREATE THE MAIN MEMORY MANAGER
        this.mmu = new Mmu();

        // LOAD ROM
        this.mbc = RomLoader.load(cartridgePath);

        // CREATE PERIPHERALS
        this.timer = new Timer(this.mmu);
        this.joypad = new Joypad(this.mmu);
        final Dma dma = new Dma(this.mmu);

        // CREATE MAIN SYSTEMS
        this.cpu = new Cpu(this.mmu);
        this.ppu = new Ppu(this.mmu, screen);
        this.apu = new Apu();

        // REGISTER ADDRESS SPACE IN MMU
        this.memoryList.add(new RandomAccessMemory("VRAM", 0x8000, 0xA000 - 0x8000)); // VRAM
        this.memoryList.add(new RandomAccessMemory("OAM RAM", 0xFE00, 0xFEA0 - 0xFE00)); // OAM RAM
        this.memoryList.add(new WRam()); // RAM
        this.memoryList.add(this.mbc); // ROM + (EXTERNAL RAM)
        this.memoryList.add(new RandomAccessMemory("HRAM", 0xFF80, 0xFFFF - 0xFF80)); // HRAM
        this.memoryList.add(new InterruptRegisters()); // IE & IF
        this.memoryList.add(this.timer); // TIMER & DIV
        this.memoryList.add(this.joypad); // JOYPAD
        this.memoryList.add(dma); // DMA
        this.memoryList.add(this.ppu); // LCD REGISTERS
        this.memoryList.add(new SerialBus());
        this.memoryList.add(this.apu);
        this.memoryList.add(new RandomAccessMemory("KEY1", Memory.KEY1, 1)); // KEY1
        this.memoryList.add(new RandomAccessMemory("VBK", Memory.VBK, 1)); // VBK
        this.memoryList.add(new RandomAccessMemory("HDMA", Memory.HDMA1, 5)); // HDMA
        this.memoryList.add(new RandomAccessMemory("RP", Memory.RP, 1)); // RP
        this.memoryList.add(new RandomAccessMemory("BCPS & BCPD & OCPS & OCPD", Memory.BCPS, 4)); // BCPS & BCPD & OCPS & OCPD
        this.memoryList.add(new RandomAccessMemory("SVBK", Memory.SVBK, 1)); // SVBK
        this.memoryList.add(new RandomAccessMemory("PROHIBITED AREA", 0xFEA0, 0x60)); // PROHIBITED AREA
        this.memoryList.add(new RandomAccessMemory("? 1", 0xFF7F, 1)); // ?
        this.memoryList.add(new RandomAccessMemory("? 2", 0xFF03, 1)); // ?
        this.memoryList.add(new RandomAccessMemory("? 3", 0xFF08, 7)); // ?
        this.memoryList.add(new RandomAccessMemory("? 4", 0xFF4C, 1)); // ?
        this.memoryList.add(new RandomAccessMemory("? 5", 0xFF4E, 1)); // ?
        this.memoryList.add(new RandomAccessMemory("? 6", 0xFF57, 17)); // ?
        this.memoryList.add(new RandomAccessMemory("? 7", 0xFF6C, 4)); // ?
        this.memoryList.add(new RandomAccessMemory("? 8", 0xFF71, 14)); // ?
        this.mmu.addMemoryUnits(this.memoryList);

        final Booter booter = new Booter(this.cpu.getRegister(), this.mmu, this.mbc);
        booter.boot();
        System.out.println("System booted.");
    }


    public boolean step() {
        this.timer.step();
        this.cpu.step();
        final boolean vBlank = this.ppu.step();
        this.apu.step();
        return vBlank;
    }


    public boolean isAudioBufferFull() {
        return this.apu.isBufferFull();
    }


    public byte[] fetchAudioSamples() {
        return this.apu.fetchSamples();
    }


    public void renderVRam(Pixmap pixmap) {
        this.ppu.renderTileMap(pixmap);
    }


    public void renderBgMap(Pixmap pixmap) {
        this.ppu.renderBgMap(pixmap);
    }


    public List<Memory> getMemoryList() {
        return this.memoryList;
    }


    public Joypad getJoypad() {
        return this.joypad;
    }


    public String getGameTitle() {
        return this.mbc.getGameName();
    }


    public Mmu getMmu() {
        return this.mmu;
    }

}
