package de.pottgames.anemulator;

import java.io.IOException;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.pottgames.anemulator.cpu.CPU;
import de.pottgames.anemulator.cpu.CallStack;
import de.pottgames.anemulator.gpu.GPU;
import de.pottgames.anemulator.input.DebugInput;
import de.pottgames.anemulator.input.KeyboardInput;
import de.pottgames.anemulator.memory.MemoryBankController;
import de.pottgames.anemulator.rom.RomLoader;

public class Start extends ApplicationAdapter {
    private final InputMultiplexer inputMultiplexer = new InputMultiplexer();
    private SpriteBatch            batch;
    private CPU                    cpu;
    private GPU                    gpu;
    private KeyboardInput          input;
    private Pixmap                 backbuffer;
    private Pixmap                 tileMap;
    private Texture                texture;
    private Texture                tileMapTexture;


    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.backbuffer = new Pixmap(160, 144, Format.RGBA8888);
        this.tileMap = new Pixmap(128, 192, Format.RGBA8888);
        this.texture = new Texture(this.backbuffer);
        this.tileMapTexture = new Texture(this.tileMap);

        MemoryBankController memoryController = null;
        try {
            final CallStack callStack = new CallStack();
            // memoryController = RomLoader.load("Dr. Mario (World).gb", callStack);
            // memoryController = RomLoader.load("Tetris (World) (Rev A).gb", callStack);
            // memoryController = RomLoader.load("Super Mario Land (World) (Rev A).gb", callStack);
            // memoryController = RomLoader.load("Alleyway (World).gb", callStack);
            // memoryController = RomLoader.load("Bionic Battler (USA).gb", callStack);
            // memoryController = RomLoader.load("Aladdin (Europe) (En,Fr,De,Es,It,Nl).gbc", callStack);
            // memoryController = RomLoader.load("Batman - The Video Game (World).gb", callStack);
            // memoryController = RomLoader.load("Boxxle II (USA, Europe).gb");
            // memoryController = RomLoader.load("cpu_instrs.gb", callStack);
            // memoryController = RomLoader.load("instr_timing.gb");
            // memoryController = RomLoader.load("cpu_instrs/02-interrupts.gb");
            // memoryController = RomLoader.load("cpu_instrs/09-op r,r.gb", callStack);
            // memoryController = RomLoader.load("cpu_instrs/03-op sp,hl.gb", callStack);
            memoryController = RomLoader.load("cpu_instrs/01-special.gb", callStack);
            this.cpu = new CPU(memoryController, callStack);
            this.gpu = new GPU(memoryController, this.backbuffer);
            this.input = new KeyboardInput();
            this.input.addListener(memoryController);
            this.input.addListener(this.cpu);
            Gdx.input.setInputProcessor(this.inputMultiplexer);
            this.inputMultiplexer.addProcessor(new DebugInput(this.cpu.getRegister(), memoryController));
            this.inputMultiplexer.addProcessor(this.input);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        Gdx.graphics.setTitle(memoryController.getGameName());
    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        boolean vBlank = false;
        while (!vBlank) {
            this.cpu.step();
            vBlank = this.gpu.step();
        }
        this.gpu.renderTileMap(this.tileMap);

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
        this.batch.dispose();
        this.texture.dispose();
        this.tileMapTexture.dispose();
        this.tileMap.dispose();
        this.backbuffer.dispose();
    }
}