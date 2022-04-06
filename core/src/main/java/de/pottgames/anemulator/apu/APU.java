package de.pottgames.anemulator.apu;

import de.pottgames.anemulator.memory.Memory;

public class APU implements Memory {
    static final float[][] WAVE_DUTY      = { { 0f, 0f, 0f, 0f, 0f, 0f, 0f, 1f }, { 1f, 0f, 0f, 0f, 0f, 0f, 0f, 1f }, { 1f, 0f, 0f, 0f, 0f, 1f, 1f, 1f },
            { 0f, 1f, 1f, 1f, 1f, 1f, 1f, 0f } };
    private SquareChannel  channel1;
    private SquareChannel  channel2;
    private final byte[]   internalBuffer = new byte[1024];
    private final byte[]   outputBuffer   = new byte[1024];
    private int            bufferPosition;
    private int            cycleCounter;
    private boolean        bufferFull;
    private int            frameSequencer;
    private int            frameSequencerCycleCounter;

    private final int[] memory = new int[0xFFFF]; // TODO: SET CORRECT SIZE


    public APU() {
        this.channel1 = new SquareChannel(Memory.NR11, Memory.NR12, Memory.NR10, Memory.NR13, Memory.NR14);
        this.channel2 = new SquareChannel(Memory.NR21, Memory.NR22, -1, Memory.NR23, Memory.NR24);
    }


    public void step() {
        if (this.isBitSet(Memory.NR52, 7)) {
            for (int i = 0; i < 4; i++) {
                this.stepInternal();
            }
        }
    }


    private void stepInternal() {
        this.cycleCounter++;
        this.frameSequencerCycleCounter++;

        boolean stepLength = false;
        boolean stepEnvelope = false;
        boolean stepSweep = false;
        if (this.frameSequencerCycleCounter >= 8192) {
            this.frameSequencerCycleCounter = 0;
            this.frameSequencer++;
            if (this.frameSequencer > 7) {
                this.frameSequencer = 0;
            }

            switch (this.frameSequencer) {
                case 0:
                case 4:
                    stepLength = true;
                    break;
                case 2:
                case 6:
                    stepLength = true;
                    stepSweep = true;
                    break;
                case 7:
                    stepEnvelope = true;
                    break;
                default:
                    break;
            }
        }

        final float[] channel1Samples = this.channel1.step(stepLength, stepEnvelope, stepSweep);
        final float[] channel2Samples = this.channel2.step(stepLength, stepEnvelope, stepSweep);

        if (this.cycleCounter == 87) {
            this.cycleCounter = 0;

            final float leftChannelSample = (channel1Samples[0] + channel2Samples[0]) / 2f;
            final float rightChannelSample = (channel1Samples[1] + channel2Samples[1]) / 2f;

            this.internalBuffer[this.bufferPosition] = (byte) (int) (128f + leftChannelSample * 127f);
            this.bufferPosition++;
            this.internalBuffer[this.bufferPosition] = (byte) (int) (128f + rightChannelSample * 127f);
            this.bufferPosition++;
            if (this.bufferPosition == 1024) {
                this.bufferPosition = 0;
                this.bufferFull = true;
                System.arraycopy(this.internalBuffer, 0, this.outputBuffer, 0, this.internalBuffer.length);
            }
        }
    }


    public boolean isBufferFull() {
        return this.bufferFull;
    }


    public byte[] fetchSamples() {
        this.bufferFull = false;
        return this.outputBuffer;
    }


    @Override
    public boolean acceptsAddress(int address) {
        if (this.channel1.acceptsAddress(address)) {
            return true;
        }

        if (this.channel2.acceptsAddress(address)) {
            return true;
        }

        switch (address) {
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
        if (this.channel1.acceptsAddress(address)) {
            return this.channel1.readByte(address);
        }

        if (this.channel2.acceptsAddress(address)) {
            return this.channel2.readByte(address);
        }

        // TODO: IMPLEMENT PROPERLY
        return this.memory[address];
    }


    @Override
    public void writeByte(int address, int value) {
        if (this.channel1.acceptsAddress(address)) {
            this.channel1.writeByte(address, value);
            return;
        }

        if (this.channel2.acceptsAddress(address)) {
            this.channel2.writeByte(address, value);
            return;
        }

        // TODO: IMPLEMENT PROPERLY
        this.memory[address] = value;
    }

}
