package de.pottgames.anemulator.apu;

import com.badlogic.gdx.utils.Disposable;

import de.pottgames.anemulator.memory.Memory;
import de.pottgames.tuningfork.Audio;
import de.pottgames.tuningfork.AudioConfig;
import de.pottgames.tuningfork.logger.ConsoleLogger;

public class APU implements Memory, Disposable {
    private static final int[][] WAVE_DUTY = { { 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 1, 1, 1 }, { 0, 1, 1, 1, 1, 1, 1, 0 } };
    private final Audio          audio;
    private SquareChannel        channel1, channel2;

    private final int[] memory = new int[0xFFFF]; // TODO: SET CORRECT SIZE


    public APU() {
        final AudioConfig config = new AudioConfig();
        config.setSimultaneousSources(0);
        config.setLogger(new ConsoleLogger());
        this.audio = Audio.init(config);

        this.channel1 = new SquareChannel(Memory.NR14, Memory.NR13, Memory.NR12, Memory.NR11, Memory.NR10);
        this.channel2 = new SquareChannel(Memory.NR24, Memory.NR23, Memory.NR22, Memory.NR21, -1);
    }


    public void step() {
        for (int i = 0; i < 4; i++) {
            this.stepInternal();
        }
    }


    private void stepInternal() {

    }


    @Override
    public boolean acceptsAddress(int address) {
        switch (address) {
            case Memory.NR10:
            case Memory.NR11:
            case Memory.NR12:
            case Memory.NR13:
            case Memory.NR14:
            case Memory.NR21:
            case Memory.NR22:
            case Memory.NR23:
            case Memory.NR24:
            case Memory.NR30:
            case Memory.NR31:
            case Memory.NR32:
            case Memory.NR33:
            case Memory.NR34:
            case Memory.NR41:
            case Memory.NR42:
            case Memory.NR43:
            case Memory.NR44:
            case Memory.NR50:
            case Memory.NR51:
            case Memory.NR52:
                return true;
        }

        if (address >= 0xFF30 && address < 0xFF40) {
            return true;
        }

        return false;
    }


    @Override
    public int readByte(int address) {
        // TODO: IMPLEMENT PROPERLY
        return this.memory[address];
    }


    @Override
    public int readWord(int address) {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public void writeByte(int address, int value) {
        // TODO: IMPLEMENT PROPERLY
        this.memory[address] = value;
    }


    @Override
    public void dispose() {
        this.channel1.dispose();
        this.channel2.dispose();
        this.audio.dispose();
    }

}
