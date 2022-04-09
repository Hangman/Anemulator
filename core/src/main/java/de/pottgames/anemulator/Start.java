package de.pottgames.anemulator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.pottgames.anemulator.input.KeyboardInput;
import de.pottgames.tuningfork.Audio;
import de.pottgames.tuningfork.PcmFormat;
import de.pottgames.tuningfork.PcmSoundSource;

public class Start extends ApplicationAdapter {
    private final InputMultiplexer inputMultiplexer = new InputMultiplexer();
    private SpriteBatch            batch;
    private KeyboardInput          input;
    private Pixmap                 backbuffer;
    private Pixmap                 tileMap;
    private Texture                texture;
    private Texture                tileMapTexture;
    private Gameboy                gameboy;
    private Audio                  audio;
    private PcmSoundSource         soundSource;


    @Override
    public void create() {
        this.audio = Audio.init();
        this.soundSource = new PcmSoundSource(48000, PcmFormat.STEREO_8_BIT);

        this.batch = new SpriteBatch();
        this.backbuffer = new Pixmap(160, 144, Format.RGBA8888);
        this.tileMap = new Pixmap(128, 192, Format.RGBA8888);
        this.texture = new Texture(this.backbuffer);
        this.tileMapTexture = new Texture(this.tileMap);

        // ANEMULATOR TEST ROMS
        // this.gameboy = new Gameboy("anemulator/hello-world.gb", this.backbuffer);
        // this.gameboy = new Gameboy("anemulator/sprite-test.gb", this.backbuffer);

        // GAMES
        // this.gameboy = new Gameboy("Dr. Mario (World).gb", this.backbuffer);
        // this.gameboy = new Gameboy("Tetris (World) (Rev A).gb", this.backbuffer);
        // this.gameboy = new Gameboy("Kirby's Dream Land (USA, Europe).gb", this.backbuffer);
        // this.gameboy = new Gameboy("Super Mario Land (World) (Rev A).gb", this.backbuffer);
        // this.gameboy = new Gameboy("Donkey Kong Land (USA, Europe) (SGB Enhanced).gb", this.backbuffer);
        // this.gameboy = new Gameboy("Pinball Deluxe (Europe).gb", this.backbuffer);
        // this.gameboy = new Gameboy("F-1 Race (World).gb", this.backbuffer);
        // this.gameboy = new Gameboy("Alleyway (World).gb", this.backbuffer); // ISSUES
        // this.gameboy = new Gameboy("Bionic Battler (USA).gb", this.backbuffer);
        // this.gameboy = new Gameboy("Aladdin (Europe) (En,Fr,De,Es,It,Nl).gbc", this.backbuffer);
        // this.gameboy = new Gameboy("Batman - The Video Game (World).gb", this.backbuffer);
        this.gameboy = new Gameboy("Boxxle II (USA, Europe).gb", this.backbuffer);

        // this.gameboy = new Gameboy("cpu_instrs.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("instr_timing.gb", this.backbuffer);
        // this.gameboy = new Gameboy("cpu_instrs/01-special.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("cpu_instrs/02-interrupts.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("cpu_instrs/03-op sp,hl.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("cpu_instrs/04-op r,imm.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("cpu_instrs/05-op rp.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("cpu_instrs/06-ld r,r.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("cpu_instrs/07-jr,jp,call,ret,rst.gb", this.backbuffer);
        // this.gameboy = new Gameboy("cpu_instrs/08-misc instrs.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("cpu_instrs/09-op r,r.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("cpu_instrs/10-bit ops.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("cpu_instrs/11-op a,(hl).gb", this.backbuffer); // PASSED

        // MOONEYE
        // this.gameboy = new Gameboy("mooneye/daa.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("mooneye/reg_f.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("mooneye/div_write.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("mooneye/rapid_di_ei.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("mooneye/if_ie_registers.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("mooneye/call_timing.gb", this.backbuffer);
        // this.gameboy = new Gameboy("mooneye/pop_timing.gb", this.backbuffer);
        // this.gameboy = new Gameboy("mooneye/ei_sequence.gb", this.backbuffer);
        // this.gameboy = new Gameboy("mooneye/tma_write_reloading.gb", this.backbuffer);

        // DMG_SOUND
        // this.gameboy = new Gameboy("dmg_sound/01-registers.gb", this.backbuffer);
        // this.gameboy = new Gameboy("dmg_sound/02-len ctr.gb", this.backbuffer);
        // this.gameboy = new Gameboy("dmg_sound/03-trigger.gb", this.backbuffer);
        // this.gameboy = new Gameboy("dmg_sound/04-sweep.gb", this.backbuffer);
        // this.gameboy = new Gameboy("dmg_sound/05-sweep details.gb", this.backbuffer);
        // this.gameboy = new Gameboy("dmg_sound/06-overflow on trigger.gb", this.backbuffer);
        // this.gameboy = new Gameboy("dmg_sound/07-len sweep period sync.gb", this.backbuffer);
        // this.gameboy = new Gameboy("dmg_sound/08-len ctr during power.gb", this.backbuffer);
        // this.gameboy = new Gameboy("dmg_sound/09-wave read while on.gb", this.backbuffer);
        // this.gameboy = new Gameboy("dmg_sound/10-wave trigger while on.gb", this.backbuffer);
        // this.gameboy = new Gameboy("dmg_sound/11-regs after power.gb", this.backbuffer);
        // this.gameboy = new Gameboy("dmg_sound/12-wave write while on.gb", this.backbuffer);

        // TEAROOM TEST ROMS
        // this.gameboy = new Gameboy("tearoom/m2_win_en_toggle.gb", this.backbuffer);

        // SETUP GDX
        this.input = new KeyboardInput();
        this.input.addListener(this.gameboy.getJoypad());
        Gdx.input.setInputProcessor(this.inputMultiplexer);
        this.inputMultiplexer.addProcessor(this.input);
        Gdx.graphics.setTitle(this.gameboy.getGameTitle());
    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        while (!this.gameboy.step()) {
            if (this.gameboy.isAudioBufferFull()) {
                final byte[] samples = this.gameboy.fetchAudioSamples();
                this.soundSource.queueSamples(samples, 0, samples.length);
                this.soundSource.play();
                while (this.soundSource.queuedBuffers() > 8) {
                    try {
                        Thread.sleep(1);
                    } catch (final InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.soundSource.unqueueProcessedBuffers();
                }
            }
        }

        this.gameboy.renderVRam(this.tileMap);

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
        this.soundSource.dispose();
        this.audio.dispose();
        this.batch.dispose();
        this.texture.dispose();
        this.tileMapTexture.dispose();
        this.tileMap.dispose();
        this.backbuffer.dispose();
    }
}