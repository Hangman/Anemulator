package de.pottgames.anemulator;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Pixmap;

import de.pottgames.anemulator.apu.APU;
import de.pottgames.anemulator.cpu.Booter;
import de.pottgames.anemulator.cpu.CPU;
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
import de.pottgames.anemulator.ppu.LcdRegisters;
import de.pottgames.anemulator.ppu.PPU;
import de.pottgames.anemulator.rom.RomLoader;

public class Gameboy {
    private final CPU          cpu;
    private final PPU          ppu;
    private final APU          apu;
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
        this.cpu = new CPU(this.mmu);
        this.ppu = new PPU(this.mmu, screen);
        this.apu = new APU();

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
        this.memoryList.add(new LcdRegisters());
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


    public void renderFrame() {
        boolean vBlank = false;
        while (!vBlank) {
            this.timer.step();
            this.cpu.step();
            vBlank = this.ppu.step();
            this.apu.step();
        }
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


    public List<Memory> getMemoryList() {
        return this.memoryList;
    }


    public Joypad getJoypad() {
        return this.joypad;
    }


    public String getGameTitle() {
        return this.mbc.getGameName();
    }

}
