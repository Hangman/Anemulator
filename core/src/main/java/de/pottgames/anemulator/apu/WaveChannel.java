package de.pottgames.anemulator.apu;

import de.pottgames.anemulator.memory.Memory;

public class WaveChannel implements Memory {
    private int[]         wavePattern = new int[0xFF40 - 0xFF30];
    private final float[] samples     = new float[2];
    private int           frequencyTimer;
    private int           frequency;
    private int           dutyPosition;
    private boolean       dacOn;
    private boolean       lengthEnabled;
    private int           lengthCounter;
    private boolean       enabled;
    private int           outputLevel;
    private int           volumeShift;


    public float[] step(boolean stepLength) {
        // STEP SYSTEMS
        if (stepLength) {
            this.stepLength();
        }

        if (this.frequencyTimer == 0) {
            this.frequencyTimer = (2048 - this.frequency) * 2;
            this.dutyPosition = this.dutyPosition + 1 & 31;
        }
        this.frequencyTimer -= 1;

        float floatSample = 0f;
        if (this.dacOn && this.enabled) {
            final int sample = this.wavePattern[this.dutyPosition / 2] >> ((this.dutyPosition & 1) != 0 ? 4 : 0) & 0x0F;
            floatSample = (sample >>> this.volumeShift) / 15f;
        }
        this.samples[0] = floatSample;
        this.samples[1] = floatSample;

        return this.samples;
    }


    private void stepLength() {
        if (this.lengthEnabled && this.lengthCounter > 0) {
            this.lengthCounter--;
            if (this.lengthCounter == 0) {
                this.enabled = false;
            }
        }
    }


    public void setDutyPosition(int value) {
        this.dutyPosition = value;
    }


    public boolean isEnabled() {
        return this.enabled;
    }


    @Override
    public boolean acceptsAddress(int address) {
        return address == Memory.NR30 || address == Memory.NR31 || address == Memory.NR32 || address == Memory.NR33 || address == Memory.NR34
                || address >= 0xFF30 && address < 0xFF40;
    }


    @Override
    public int readByte(int address) {
        if (address == Memory.NR30) {
            return (this.dacOn ? 1 : 0) << 7 | 0x7F;
        }
        if (address == Memory.NR31) {
            return 0xFF;
        }
        if (address == Memory.NR32) {
            return this.outputLevel << 5 | 0x9F;
        }
        if (address == Memory.NR33) {
            return 0xFF;
        }
        if (address == Memory.NR34) {
            return (this.lengthEnabled ? 1 : 0) << 6 | 0b1011_1111;
        }
        if (address >= 0xFF30 && address < 0xFF40) {
            return this.wavePattern[address - 0xFF30];
        }

        throw new RuntimeException("Invalid address: " + Integer.toHexString(address));
    }


    @Override
    public void writeByte(int address, int value) {
        if (address == Memory.NR30) {
            this.dacOn = (value >>> 7 & 0b1) != 0;
            if (!this.dacOn) {
                this.enabled = false;
            }
            return;
        }
        if (address == Memory.NR31) {
            this.lengthCounter = 256 - value;
            return;
        }
        if (address == Memory.NR32) {
            this.outputLevel = value >>> 5 & 0b11;
            switch (this.outputLevel) {
                case 0b00:
                    this.volumeShift = 4;
                    break;
                case 0b01:
                    this.volumeShift = 0;
                    break;
                case 0b10:
                    this.volumeShift = 1;
                    break;
                case 0b11:
                    this.volumeShift = 2;
                    break;
                default:
                    throw new RuntimeException("Invalid address: " + Integer.toHexString(address));
            }
            return;
        }
        if (address == Memory.NR33) {
            this.frequency = this.frequency & 0x700 | value;
            return;
        }
        if (address == Memory.NR34) {
            this.frequency = this.frequency & 0xFF | (value & 0x7) << 8;
            this.lengthEnabled = (value >>> 6 & 0b1) != 0;
            if (this.lengthCounter == 0) {
                this.lengthCounter = 256;
            }
            final boolean trigger = value >>> 7 != 0;
            if (trigger && this.dacOn) {
                this.enabled = true;
            }
            return;
        }
        if (address >= 0xFF30 && address < 0xFF40) {
            // If aiming for 100% accuracy, the write must be offset by the current duty position when the channel is enabled. We ignore this fact.
            this.wavePattern[address - 0xFF30] = value;
            return;
        }

        throw new RuntimeException("Invalid address: " + Integer.toHexString(address));
    }

}
