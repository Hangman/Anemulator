package de.pottgames.anemulator;

import java.io.IOException;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.pottgames.anemulator.cpu.CPU;
import de.pottgames.anemulator.gpu.GPU;
import de.pottgames.anemulator.input.KeyboardInput;
import de.pottgames.anemulator.memory.MemoryBankController;
import de.pottgames.anemulator.rom.RomLoader;

public class Start extends ApplicationAdapter {
    private SpriteBatch   batch;
    private CPU           cpu;
    private GPU           gpu;
    private KeyboardInput input;
    private Pixmap        backbuffer;
    private Pixmap        bgMap;
    private Texture       texture;
    private Texture       bgMapTexture;


    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.backbuffer = new Pixmap(160, 144, Format.RGBA8888);
        this.bgMap = new Pixmap(256, 256, Format.RGBA8888);
        this.texture = new Texture(this.backbuffer);
        this.bgMapTexture = new Texture(this.bgMap);

        MemoryBankController memoryController = null;
        try {
            memoryController = RomLoader.load("Tetris (World) (Rev A).gb");
            // memoryController = RomLoader.load("Alleyway (World).gb");
            // memoryController = RomLoader.load("Bionic Battler (USA).gb");
            // memoryController = RomLoader.load("Boxxle II (USA, Europe).gb");
            // memoryController = RomLoader.load("cpu_instrs.gb");
            this.cpu = new CPU(memoryController);
            this.gpu = new GPU(memoryController, this.backbuffer);
            this.input = new KeyboardInput();
            this.input.addListener(memoryController);
            this.input.addListener(this.cpu);
            Gdx.input.setInputProcessor(this.input);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        Gdx.graphics.setTitle(memoryController.getGameName());
    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (int i = 0; i < 69905; i++) {
            this.cpu.step();
            this.gpu.step();
        }
        this.gpu.renderFullBgMap(this.bgMap);

        this.batch.begin();
        this.texture.draw(this.backbuffer, 0, 0);
        this.batch.draw(this.texture, 0f, 0f, 160f * 2f, 144f * 2f);
        this.bgMapTexture.draw(this.bgMap, 0, 0);
        this.batch.draw(this.bgMapTexture, 160, 144);
        this.batch.end();
    }


    @Override
    public void dispose() {
        this.batch.dispose();
        this.texture.dispose();
        this.bgMapTexture.dispose();
        this.bgMap.dispose();
        this.backbuffer.dispose();
    }
}