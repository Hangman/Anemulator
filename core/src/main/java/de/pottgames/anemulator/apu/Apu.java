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

package de.pottgames.anemulator.apu;

import de.pottgames.anemulator.memory.Memory;

public class Apu implements Memory {
    static final float[][]      WAVE_DUTY      = { { 0f, 0f, 0f, 0f, 0f, 0f, 0f, 1f }, { 1f, 0f, 0f, 0f, 0f, 0f, 0f, 1f }, { 1f, 0f, 0f, 0f, 0f, 1f, 1f, 1f },
            { 0f, 1f, 1f, 1f, 1f, 1f, 1f, 0f } };
    private final SquareChannel channel1;
    private final SquareChannel channel2;
    private final WaveChannel   channel3;
    private final NoiseChannel  channel4;
    private int                 channelControlRegister;
    private int                 channelSelectionRegister;
    private final byte[]        internalBuffer = new byte[1024];
    private final byte[]        outputBuffer   = new byte[1024];
    private int                 bufferPosition;
    private int                 cycleCounter;
    private boolean             bufferFull;
    private int                 frameSequencer;
    private int                 frameSequencerCycleCounter;
    private boolean             enabled;


    public Apu() {
        this.channel1 = new SquareChannel(Memory.NR11, Memory.NR12, true, Memory.NR13, Memory.NR14);
        this.channel2 = new SquareChannel(Memory.NR21, Memory.NR22, false, Memory.NR23, Memory.NR24);
        this.channel3 = new WaveChannel();
        this.channel4 = new NoiseChannel();
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
        final float[] channel3Samples = this.channel3.step(stepLength);
        final float[] channel4Samples = this.channel4.step(stepLength, stepEnvelope);

        if (this.cycleCounter == 87) {
            this.cycleCounter = 0;

            // CHANNEL PANNING
            if ((this.channelSelectionRegister & 0b1000_0000) == 0) {
                // channel 4 left
                channel4Samples[0] = 0f;
            }
            if ((this.channelSelectionRegister & 0b0100_0000) == 0) {
                // channel 3 left
                channel3Samples[0] = 0f;
            }
            if ((this.channelSelectionRegister & 0b0010_0000) == 0) {
                // channel 2 left
                channel2Samples[0] = 0f;
            }
            if ((this.channelSelectionRegister & 0b0001_0000) == 0) {
                // channel 1 left
                channel1Samples[0] = 0f;
            }
            if ((this.channelSelectionRegister & 0b0000_1000) == 0) {
                // channel 4 right
                channel4Samples[1] = 0f;
            }
            if ((this.channelSelectionRegister & 0b0000_0100) == 0) {
                // channel 3 right
                channel3Samples[1] = 0f;
            }
            if ((this.channelSelectionRegister & 0b0000_0010) == 0) {
                // channel 2 right
                channel2Samples[1] = 0f;
            }
            if ((this.channelSelectionRegister & 0b0000_0001) == 0) {
                // channel 1 right
                channel1Samples[1] = 0f;
            }

            // MIX CHANNELS
            float leftChannelSample = (channel1Samples[0] + channel2Samples[0] + channel3Samples[0] + channel4Samples[0]) / 4f;
            float rightChannelSample = (channel1Samples[1] + channel2Samples[1] + channel3Samples[1] + channel4Samples[1]) / 4f;

            // MASTER PANNING
            final float leftPanFactor = (this.channelControlRegister >>> 4 & 0b111) / 7f;
            final float rightPanFactor = (this.channelControlRegister & 0b111) / 7f;
            leftChannelSample *= leftPanFactor;
            rightChannelSample *= rightPanFactor;

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


    private void enabledAPU(boolean enabled) {
        if (this.enabled && !enabled) {
            // TURN OFF
            this.writeByte(Memory.NR10, 0);
            this.writeByte(Memory.NR11, 0);
            this.writeByte(Memory.NR12, 0);
            this.writeByte(Memory.NR13, 0);
            this.writeByte(Memory.NR14, 0);
            this.writeByte(Memory.NR20, 0);
            this.writeByte(Memory.NR21, 0);
            this.writeByte(Memory.NR22, 0);
            this.writeByte(Memory.NR23, 0);
            this.writeByte(Memory.NR24, 0);
            this.writeByte(Memory.NR30, 0);
            this.writeByte(Memory.NR31, 0);
            this.writeByte(Memory.NR32, 0);
            this.writeByte(Memory.NR33, 0);
            this.writeByte(Memory.NR34, 0);
            this.writeByte(Memory.NR40, 0);
            this.writeByte(Memory.NR41, 0);
            this.writeByte(Memory.NR42, 0);
            this.writeByte(Memory.NR43, 0);
            this.writeByte(Memory.NR44, 0);
            this.writeByte(Memory.NR50, 0);
            this.writeByte(Memory.NR51, 0);
            for (int i = 0xFF30; i < 0xFF40; i++) {
                this.writeByte(i, 0);
            }
            this.enabled = false;

        } else if (!this.enabled && enabled) {
            // TURN ON
            this.enabled = true;
            this.frameSequencer = 0;
            this.channel1.setDutyPosition(0);
            this.channel2.setDutyPosition(0);
            this.channel3.setDutyPosition(0);
        }
    }


    @Override
    public boolean acceptsAddress(int address) {
        return address == Memory.NR50 || address == Memory.NR51 || address == Memory.NR52 || address == Memory.NR20 || address == Memory.NR40
                || this.channel1.acceptsAddress(address) || this.channel2.acceptsAddress(address) || this.channel3.acceptsAddress(address)
                || this.channel4.acceptsAddress(address) || address >= 0xFF27 && address <= 0xFF2F;
    }


    @Override
    public int readByte(int address) {
        if (!this.enabled && address != Memory.NR52 && (address < 0xFF30 || address >= 0xFF40)) {
            return 0xFF;
        }
        if (this.channel1.acceptsAddress(address)) {
            return this.channel1.readByte(address);
        }
        if (this.channel2.acceptsAddress(address)) {
            return this.channel2.readByte(address);
        }
        if (this.channel3.acceptsAddress(address)) {
            return this.channel3.readByte(address);
        }
        if (this.channel4.acceptsAddress(address)) {
            return this.channel4.readByte(address);
        }
        if (address == Memory.NR50) {
            return this.channelControlRegister;
        }
        if (address == Memory.NR51) {
            return this.channelSelectionRegister;
        }
        if (address == Memory.NR52) {
            int result = (this.enabled ? 1 : 0) << 7;
            result |= 0b0111_0000;
            result |= (this.channel4.isEnabled() ? 1 : 0) << 3;
            result |= (this.channel3.isEnabled() ? 1 : 0) << 2;
            result |= (this.channel2.isEnabled() ? 1 : 0) << 1;
            result |= this.channel1.isEnabled() ? 1 : 0;
            return result;
        }
        if (address == Memory.NR20 || address == Memory.NR40) {
            return 0xFF;
        }
        if (address >= 0xFF27 && address <= 0xFF2F) {
            return 0xFF;
        }

        throw new RuntimeException("Invalid address: " + Integer.toHexString(address));
    }


    @Override
    public void writeByte(int address, int value) {
        if (!this.enabled && address != Memory.NR52 && (address < 0xFF30 || address >= 0xFF40)) {
            return;
        }
        if (this.channel1.acceptsAddress(address)) {
            this.channel1.writeByte(address, value);
            return;
        }
        if (this.channel2.acceptsAddress(address)) {
            this.channel2.writeByte(address, value);
            return;
        }
        if (this.channel3.acceptsAddress(address)) {
            this.channel3.writeByte(address, value);
            return;
        }
        if (this.channel4.acceptsAddress(address)) {
            this.channel4.writeByte(address, value);
            return;
        }
        if (address == Memory.NR50) {
            this.channelControlRegister = value;
            return;
        }
        if (address == Memory.NR51) {
            this.channelSelectionRegister = value;
            return;
        }
        if (address == Memory.NR52) {
            this.enabledAPU((value & 0b1000_0000) > 0);
            return;
        }
        if (address == Memory.NR20 || address == Memory.NR40) {
            return;
        }
        if (address >= 0xFF27 && address <= 0xFF2F) {
            return;
        }

        throw new RuntimeException("Invalid address: " + Integer.toHexString(address));
    }


    @Override
    public String toString() {
        return "APU";
    }

}
