package de.pottgames.anemulator;

import java.io.IOException;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.pottgames.anemulator.cpu.CPU;
import de.pottgames.anemulator.memory.MemoryController;
import de.pottgames.anemulator.rom.RomLoader;

public class Start extends ApplicationAdapter {
    private SpriteBatch batch;
    private CPU         cpu;


    @Override
    public void create() {
        this.batch = new SpriteBatch();
        MemoryController memoryController;
        try {
            // memoryController = RomLoader.load("Tetris (World) (Rev A).gb");
            memoryController = RomLoader.loadBootstrapRom("[BIOS] Nintendo Game Boy Boot ROM (World).gb");
            this.cpu = new CPU(memoryController);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void render() {
        int cycles = 0;
        while (cycles < 70224) {
            cycles += this.cpu.step();
        }

        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.batch.begin();

        this.batch.end();
    }


    @Override
    public void dispose() {
        this.batch.dispose();
    }
}