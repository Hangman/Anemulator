package de.pottgames.anemulator.apu;

import de.pottgames.anemulator.memory.Memory;

public class NoiseChannel implements Memory {
    private final float[] samples = new float[2];
    private int           polynomialRegister;
    private int           lengthCounter;
    private boolean       incrementing;
    private int           initialVolume;
    private int           period;
    private boolean       dacOn;
    private boolean       enabled;
    private boolean       lengthEnabled;
    private int           lfsr;
    private int           periodTimer;
    private int           volume;
    private int           frequencyTimer;


    public float[] step(boolean stepLength, boolean stepEnvelope) {
        // STEP SYSTEMS
        if (stepLength) {
            this.stepLength();
        }
        if (stepEnvelope) {
            this.stepEnvelope();
        }

        if (this.frequencyTimer == 0) {
            final int divisorCode = this.polynomialRegister & 0x07;
            this.frequencyTimer = (divisorCode == 0 ? 8 : divisorCode << 4) << (this.polynomialRegister >>> 4);
            final int xorResult = this.lfsr & 0b01 ^ (this.lfsr & 0b10) >> 1;
            this.lfsr = this.lfsr >> 1 | xorResult << 14;
            if ((this.polynomialRegister >>> 3 & 0b01) != 0) {
                this.lfsr &= ~(1 << 6);
                this.lfsr |= xorResult << 6;
            }
        }

        this.frequencyTimer -= 1;

        float sample = 0f;
        if (this.dacOn && this.enabled) {
            final float input = (float) (~this.lfsr & 0b1) * this.volume;
            sample = input / 15f;
        }

        this.samples[0] = sample;
        this.samples[1] = sample;

        return this.samples;
    }


    private void stepLength() {
        if (this.lengthEnabled && this.lengthCounter > 0) {
            this.lengthCounter -= 1;
            if (this.lengthCounter == 0) {
                this.enabled = false;
            }
        }
    }


    private void stepEnvelope() {
        if (this.period != 0) {
            if (this.periodTimer > 0) {
                this.periodTimer -= 1;
            }
            if (this.periodTimer == 0) {
                this.periodTimer = this.period;
                if (this.volume < 0xF && this.incrementing || this.volume > 0 && !this.incrementing) {
                    if (this.incrementing) {
                        this.volume++;
                    } else {
                        this.volume--;
                    }
                }
            }
        }
    }


    @Override
    public boolean acceptsAddress(int address) {
        return address == 0xFF1F || address == Memory.NR41 || address == Memory.NR42 || address == Memory.NR43 || address == Memory.NR44;
    }


    @Override
    public int readByte(int address) {
        if (address == 0xFF1F) {
            return 0xFF;
        }
        if (address == Memory.NR41) {
            return 0xFF;
        }
        if (address == Memory.NR42) {
            return this.initialVolume << 4 | (this.incrementing ? 0x08 : 0) | this.period;
        }
        if (address == Memory.NR43) {
            return this.polynomialRegister;
        }
        if (address == Memory.NR44) {
            return (this.lengthEnabled ? 1 : 0) << 6 | 0b1011_1111;
        }

        throw new RuntimeException("Invalid address");
    }


    @Override
    public void writeByte(int address, int value) {
        if (address == 0xFF1F) {
            return;
        }
        if (address == Memory.NR41) {
            this.lengthCounter = 64 - (value & 0b11_1111);
            return;
        }
        if (address == Memory.NR42) {
            this.incrementing = (value & 0x08) != 0;
            this.initialVolume = value >> 4;
            this.period = value & 0x07;
            this.dacOn = (value & 0b1111_1000) != 0;

            if (!this.dacOn) {
                this.enabled = false;
            }
            return;
        }
        if (address == Memory.NR43) {
            this.polynomialRegister = value;
            return;
        }
        if (address == Memory.NR44) {
            this.lengthEnabled = (value >> 6 & 0b1) != 0;
            if (this.lengthCounter == 0) {
                this.lengthCounter = 64;
            }
            final boolean trigger = value >> 7 != 0;
            if (trigger && this.dacOn) {
                this.enabled = true;
            }
            if (trigger) {
                this.lfsr = 0x7FFF;
                this.periodTimer = this.period;
                this.volume = this.initialVolume;
            }
            return;
        }

        throw new RuntimeException("Invalid address");
    }

}
