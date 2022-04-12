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
    private Pixmap                 bgMap;
    private Texture                texture;
    private Texture                tileMapTexture;
    private Texture                bgMapTexture;
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
        this.bgMap = new Pixmap(256, 256, Format.RGBA8888);
        this.texture = new Texture(this.backbuffer);
        this.tileMapTexture = new Texture(this.tileMap);
        this.bgMapTexture = new Texture(this.bgMap);

        // ANEMULATOR TEST ROMS
        // this.gameboy = new Gameboy("anemulator/hello-world.gb", this.backbuffer);
        // this.gameboy = new Gameboy("anemulator/sprite-test.gb", this.backbuffer);

        // GAMES
        // this.gameboy = new Gameboy("Dr. Mario (World).gb", this.backbuffer);
        // this.gameboy = new Gameboy("Tetris (World) (Rev A).gb", this.backbuffer);
        // this.gameboy = new Gameboy("Kirby's Dream Land (USA, Europe).gb", this.backbuffer);
        this.gameboy = new Gameboy("Super Mario Land (World) (Rev A).gb", this.backbuffer);
        // this.gameboy = new Gameboy("Donkey Kong Land (USA, Europe) (SGB Enhanced).gb", this.backbuffer);
        // this.gameboy = new Gameboy("Pinball Deluxe (Europe).gb", this.backbuffer);
        // this.gameboy = new Gameboy("F-1 Race (World).gb", this.backbuffer);
        // this.gameboy = new Gameboy("Alleyway (World).gb", this.backbuffer); // ISSUES
        // this.gameboy = new Gameboy("Bionic Battler (USA).gb", this.backbuffer);
        // this.gameboy = new Gameboy("Aladdin (Europe) (En,Fr,De,Es,It,Nl).gbc", this.backbuffer);
        // this.gameboy = new Gameboy("Batman - The Video Game (World).gb", this.backbuffer);
        // this.gameboy = new Gameboy("Boxxle II (USA, Europe).gb", this.backbuffer);
        // this.gameboy = new Gameboy("Mega Man - Dr. Wily's Revenge (E) [!].gb", this.backbuffer);
        // this.gameboy = new Gameboy("Super Mario Land 2 - 6 Golden Coins (UE) (V1.2) [!].gb", this.backbuffer);

        // this.gameboy = new Gameboy("cpu_instrs.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("instr_timing.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("cpu_instrs/01-special.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("cpu_instrs/02-interrupts.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("cpu_instrs/03-op sp,hl.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("cpu_instrs/04-op r,imm.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("cpu_instrs/05-op rp.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("cpu_instrs/06-ld r,r.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("cpu_instrs/07-jr,jp,call,ret,rst.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("cpu_instrs/08-misc instrs.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("cpu_instrs/09-op r,r.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("cpu_instrs/10-bit ops.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("cpu_instrs/11-op a,(hl).gb", this.backbuffer); // PASSED

        // MOONEYE
        // this.gameboy = new Gameboy("mooneye/acceptance/add_sp_e_timing.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/boot_div2-S.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/boot_div-dmg0.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/boot_div-dmgABCmgb.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/boot_div-S.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/boot_hwio-dmg0.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/boot_hwio-dmgABCmgb.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/boot_hwio-S.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/boot_regs-dmg0.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/boot_regs-dmgABC.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("mooneye/acceptance/boot_regs-mgb.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/boot_regs-sgb.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/boot_regs-sgb2.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/call_cc_timing.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/call_cc_timing2.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/call_timing.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/call_timing2.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/di_timing-GS.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/div_timing.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/ei_sequence.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/ei_timing.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("mooneye/acceptance/halt_ime0_ei.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("mooneye/acceptance/halt_ime0_nointr_timing.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/halt_ime1_timing.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("mooneye/acceptance/halt_ime1_timing2-GS.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/if_ie_registers.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("mooneye/acceptance/intr_timing.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/jp_cc_timing.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/jp_timing.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/ld_hl_sp_e_timing.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/oam_dma_restart.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/oam_dma_start.gb", this.backbuffer); // ?
        // this.gameboy = new Gameboy("mooneye/acceptance/oam_dma_timing.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/pop_timing.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/push_timing.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/rapid_di_ei.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("mooneye/acceptance/ret_cc_timing.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/ret_timing.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/reti_intr_timing.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("mooneye/acceptance/reti_timing.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/rst_timing.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/bits/mem_oam.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("mooneye/acceptance/bits/reg_f.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("mooneye/acceptance/bits/unused_hwio-GS.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/instr/daa.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("mooneye/acceptance/interrupts/ie_push.gb", this.backbuffer); // CRASH
        // this.gameboy = new Gameboy("mooneye/acceptance/oam_dma/basic.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("mooneye/acceptance/oam_dma/reg_read.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("mooneye/acceptance/oam_dma/sources-GS.gb", this.backbuffer); // UNSUPPORTED MBC
        // this.gameboy = new Gameboy("mooneye/acceptance/ppu/hblank_ly_scx_timing-GS.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/ppu/intr_1_2_timing-GS.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/ppu/intr_2_0_timing.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/ppu/intr_2_mode0_timing.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/ppu/intr_2_mode0_timing_sprites.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/ppu/intr_2_mode3_timing.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/ppu/intr_2_oam_ok_timing.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/ppu/lcdon_timing-GS.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/ppu/lcdon_write_timing-GS.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/ppu/stat_irq_blocking.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/ppu/stat_lyc_onoff.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/ppu/vblank_stat_intr-GS.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/timer/div_write.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("mooneye/acceptance/timer/rapid_toggle.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/timer/tim00.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/timer/tim00_div_trigger.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/timer/tim01.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/timer/tim01_div_trigger.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/timer/tim10.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/timer/tim10_div_trigger.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/timer/tim11.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/timer/tim11_div_trigger.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/timer/tima_reload.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/timer/tima_write_reloading.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/acceptance/timer/tma_write_reloading.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("mooneye/manual-only/sprite_priority.gb", this.backbuffer); // ?

        // DMG_SOUND
        // this.gameboy = new Gameboy("dmg_sound/01-registers.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("dmg_sound/02-len ctr.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("dmg_sound/03-trigger.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("dmg_sound/04-sweep.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("dmg_sound/05-sweep details.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("dmg_sound/06-overflow on trigger.gb", this.backbuffer); // PASSED
        // this.gameboy = new Gameboy("dmg_sound/07-len sweep period sync.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("dmg_sound/08-len ctr during power.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("dmg_sound/09-wave read while on.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("dmg_sound/10-wave trigger while on.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("dmg_sound/11-regs after power.gb", this.backbuffer); // FAILED
        // this.gameboy = new Gameboy("dmg_sound/12-wave write while on.gb", this.backbuffer); // FAILED

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
        this.gameboy.renderBgMap(this.bgMap);

        this.batch.begin();

        // RENDER GAMEBOY SCREEN
        this.texture.draw(this.backbuffer, 0, 0);
        this.batch.draw(this.texture, 0f, 0f, 160f, 144f);

        // RENDER VRAM TILE MAP
        this.tileMapTexture.draw(this.tileMap, 0, 0);
        this.batch.draw(this.tileMapTexture, 360f, 144f);

        // RENDER BG MAP
        this.bgMapTexture.draw(this.bgMap, 0, 0);
        this.batch.draw(this.bgMapTexture, 0f, 170f);

        this.batch.end();
    }


    @Override
    public void dispose() {
        this.soundSource.dispose();
        this.audio.dispose();
        this.batch.dispose();
        this.texture.dispose();
        this.tileMapTexture.dispose();
        this.bgMapTexture.dispose();
        this.tileMap.dispose();
        this.backbuffer.dispose();
        this.bgMap.dispose();
    }
}