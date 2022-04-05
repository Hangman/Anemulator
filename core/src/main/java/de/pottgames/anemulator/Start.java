package de.pottgames.anemulator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.pottgames.anemulator.apu.APU;
import de.pottgames.anemulator.cpu.Booter;
import de.pottgames.anemulator.cpu.CPU;
import de.pottgames.anemulator.cpu.Timer;
import de.pottgames.anemulator.input.DebugInput;
import de.pottgames.anemulator.input.Joypad;
import de.pottgames.anemulator.input.KeyboardInput;
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

public class Start extends ApplicationAdapter {
    private final InputMultiplexer inputMultiplexer = new InputMultiplexer();
    private SpriteBatch            batch;
    private CPU                    cpu;
    private PPU                    ppu;
    private APU                    apu;
    private KeyboardInput          input;
    private Pixmap                 backbuffer;
    private Pixmap                 tileMap;
    private Texture                texture;
    private Texture                tileMapTexture;
    private int                    steps            = 0;
    private Mbc                    mbc;
    private Mmu                    mmu;
    private Timer                  timer;
    private Joypad                 joypad;


    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.backbuffer = new Pixmap(160, 144, Format.RGBA8888);
        this.tileMap = new Pixmap(128, 192, Format.RGBA8888);
        this.texture = new Texture(this.backbuffer);
        this.tileMapTexture = new Texture(this.tileMap);

        // CREATE THE MAIN MEMORY MANAGER
        this.mmu = new Mmu();

        // LOAD ROM
        // this.mbc = RomLoader.load("Dr. Mario (World).gb");
        // this.mbc = RomLoader.load("Tetris (World) (Rev A).gb");
        this.mbc = RomLoader.load("Super Mario Land (World) (Rev A).gb");
        // this.memoryController = RomLoader.load("Donkey Kong Land (USA, Europe) (SGB Enhanced).gb");
        // this.memoryController = RomLoader.load("Pinball Deluxe (Europe).gb");
        // this.memoryController = RomLoader.load("F-1 Race (World).gb");
        // this.memoryController = RomLoader.load("Alleyway (World).gb");
        // this.memoryController = RomLoader.load("Bionic Battler (USA).gb");
        // memoryController = RomLoader.load("Aladdin (Europe) (En,Fr,De,Es,It,Nl).gbc");
        // this.memoryController = RomLoader.load("Batman - The Video Game (World).gb");
        // this.memoryController = RomLoader.load("Boxxle II (USA, Europe).gb");
        // this.memoryController = RomLoader.load("cpu_instrs.gb"); // PASSED
        // this.memoryController = RomLoader.load("instr_timing.gb");
        // this.memoryController = RomLoader.load("cpu_instrs/01-special.gb"); // PASSED
        // this.memoryController = RomLoader.load("cpu_instrs/02-interrupts.gb"); // PASSED
        // this.memoryController = RomLoader.load("cpu_instrs/03-op sp,hl.gb"); // PASSED
        // this.memoryController = RomLoader.load("cpu_instrs/04-op r,imm.gb"); // PASSED
        // this.memoryController = RomLoader.load("cpu_instrs/05-op rp.gb"); // PASSED
        // memoryController = RomLoader.load("cpu_instrs/06-ld r,r.gb"); // PASSED
        // this.memoryController = RomLoader.load("cpu_instrs/07-jr,jp,call,ret,rst.gb");
        // this.memoryController = RomLoader.load("cpu_instrs/08-misc instrs.gb"); // PASSED
        // this.memoryController = RomLoader.load("cpu_instrs/09-op r,r.gb"); // PASSED
        // this.memoryController = RomLoader.load("cpu_instrs/10-bit ops.gb"); // PASSED
        // this.memoryController = RomLoader.load("cpu_instrs/11-op a,(hl).gb"); // PASSED
        // this.memoryController = RomLoader.load("mooneye/daa.gb"); // PASSED
        // this.memoryController = RomLoader.load("mooneye/reg_f.gb"); // PASSED
        // this.memoryController = RomLoader.load("mooneye/div_write.gb"); // PASSED
        // this.memoryController = RomLoader.load("mooneye/rapid_di_ei.gb"); // PASSED
        // this.memoryController = RomLoader.load("mooneye/if_ie_registers.gb"); // PASSED
        // this.memoryController = RomLoader.load("mooneye/call_timing.gb");
        // this.memoryController = RomLoader.load("mooneye/pop_timing.gb");
        // this.memoryController = RomLoader.load("mooneye/ei_sequence.gb");
        // this.memoryController = RomLoader.load("mooneye/tma_write_reloading.gb");

        // CREATE PERIPHERALS
        this.timer = new Timer(this.mmu);
        this.joypad = new Joypad(this.mmu);
        final Dma dma = new Dma(this.mmu);

        // CREATE MAIN SYSTEMS
        this.cpu = new CPU(this.mmu);
        this.ppu = new PPU(this.mmu, this.backbuffer);
        this.apu = new APU();

        // REGISTER ADDRESS SPACE IN MMU
        this.mmu.addMemoryUnit(new RandomAccessMemory(0x8000, 0xA000 - 0x8000)); // VRAM
        this.mmu.addMemoryUnit(new RandomAccessMemory(0xFE00, 0xFEA0 - 0xFE00)); // OAM RAM
        this.mmu.addMemoryUnit(new WRam()); // RAM
        this.mmu.addMemoryUnit(this.mbc); // ROM + (EXTERNAL RAM)
        this.mmu.addMemoryUnit(new RandomAccessMemory(0xFF80, 0xFFFF - 0xFF80)); // HRAM
        this.mmu.addMemoryUnit(new InterruptRegisters()); // IE & IF
        this.mmu.addMemoryUnit(this.timer); // TIMER & DIV
        this.mmu.addMemoryUnit(this.joypad); // JOYPAD
        this.mmu.addMemoryUnit(dma); // DMA
        this.mmu.addMemoryUnit(new LcdRegisters());
        this.mmu.addMemoryUnit(new SerialBus());
        this.mmu.addMemoryUnit(this.apu);
        this.mmu.addMemoryUnit(new RandomAccessMemory(Memory.KEY1, 1)); // KEY1
        this.mmu.addMemoryUnit(new RandomAccessMemory(Memory.VBK, 1)); // VBK
        this.mmu.addMemoryUnit(new RandomAccessMemory(Memory.HDMA1, 5)); // HDMA
        this.mmu.addMemoryUnit(new RandomAccessMemory(Memory.RP, 1)); // RP
        this.mmu.addMemoryUnit(new RandomAccessMemory(Memory.BCPS, 4)); // BCPS & BCPD & OCPS & OCPD
        this.mmu.addMemoryUnit(new RandomAccessMemory(Memory.SVBK, 1)); // SVBK
        this.mmu.addMemoryUnit(new RandomAccessMemory(0xFEA0, 0x60)); // PROHIBITED AREA
        this.mmu.addMemoryUnit(new RandomAccessMemory(0xFF7F, 1)); // ?

        // SETUP GDX
        this.input = new KeyboardInput();
        this.input.addListener(this.joypad);
        Gdx.input.setInputProcessor(this.inputMultiplexer);
        this.inputMultiplexer.addProcessor(new DebugInput(this.cpu.getRegister(), this));
        this.inputMultiplexer.addProcessor(this.input);
        Gdx.graphics.setTitle(this.mbc.getGameName());

        // BOOT THE GAMEBOY
        final Booter booter = new Booter(this.cpu.getRegister(), this.mmu, this.mbc);
        booter.boot();
        System.out.println("System booted.");

    }


    public void nextStep(int steps) {
        this.steps = steps;
    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // while ((this.steps > 0 || !this.memoryController.isBooted()) && this.equal) {
        // EmulatorState myState = null;
        // while (myState == null) {
        // myState = this.cpu.step();
        // this.gpu.step();
        // }
        // this.steps--;
        // }

        boolean vBlank = false;
        while (!vBlank) {
            this.timer.step();
            this.cpu.step();
            vBlank = this.ppu.step();
            this.apu.step();
        }
        this.ppu.renderTileMap(this.tileMap);

        this.batch.begin();

        // RENDER GAMEBOY SCREEN
        this.texture.draw(this.backbuffer, 0, 0);
        this.batch.draw(this.texture, 0f, 0f, 160f, 144f);

        // RENDER VRAM TILE MAP
        this.tileMapTexture.draw(this.tileMap, 0, 0);
        this.batch.draw(this.tileMapTexture, 360f, 144f);

        this.batch.end();
    }


    @Override
    public void dispose() {
        this.apu.dispose();
        this.batch.dispose();
        this.texture.dispose();
        this.tileMapTexture.dispose();
        this.tileMap.dispose();
        this.backbuffer.dispose();
    }
}