package de.pottgames.anemulator.apu;

import de.pottgames.anemulator.memory.Memory;

public class SquareChannel implements Memory {
    private final int            volumeRegisterAddress;
    private final VolumeEnvelope volumeEnvelope;
    private Sweep                sweep;
    private int                  frequencyTimer;
    private int                  dutyPosition;
    private final int            lengthDutyRegisterAddress;
    private int                  lengthDutyRegister;
    private final int            freqLowRegisterAddress;
    private final int            freqHighRegisterAddress;
    private int                  freqLowRegister;
    private int                  freqHighRegister;
    private float[]              samples = new float[2];
    private boolean              enabled;
    private int                  lengthTimer;


    public SquareChannel(int lengthRegisterAddress, int volumeRegisterAddress, boolean useSweep, int frequencyLowDataRegisterAddress,
            int frequencyHighDataRegisterAddress) {
        this.volumeRegisterAddress = volumeRegisterAddress;
        this.lengthDutyRegisterAddress = lengthRegisterAddress;
        this.freqLowRegisterAddress = frequencyLowDataRegisterAddress;
        this.freqHighRegisterAddress = frequencyHighDataRegisterAddress;
        this.volumeEnvelope = new VolumeEnvelope(volumeRegisterAddress);
        if (useSweep) {
            this.sweep = new Sweep(this::disableChannel);
        }
    }


    public float[] step(boolean stepLength, boolean stepEnvelope, boolean stepSweep) {
        if (this.enabled && this.isDacOn()) {
            // STEP SYSTEMS
            if (stepLength) {
                this.stepLength();
                if (!this.enabled) {
                    this.samples[0] = 0f;
                    this.samples[1] = 0f;
                    return this.samples;
                }
            }
            if (stepEnvelope) {
                this.volumeEnvelope.step();
            }
            if (stepSweep && this.sweep != null) {
                this.sweep.step();
            }

            final float[] duty = APU.WAVE_DUTY[this.lengthDutyRegister >>> 6];
            int frequency = this.getFrequency();
            if (this.sweep != null) {
                frequency = this.sweep.getFrequency(frequency);
            }

            this.frequencyTimer--;
            if (this.frequencyTimer <= 0) {
                this.frequencyTimer = (2048 - frequency) * 4;
                this.dutyPosition++;
                if (this.dutyPosition > 7) {
                    this.dutyPosition = 0;
                }
            }

            this.samples[0] = duty[this.dutyPosition];
            this.samples[1] = duty[this.dutyPosition];
            this.volumeEnvelope.modifySamples(this.samples);
        } else {
            this.samples[0] = 0f;
            this.samples[1] = 0f;
        }

        return this.samples;
    }


    private void stepLength() {
        if (this.isLengthTimerEnabled()) {
            this.lengthTimer--;
            if (this.lengthTimer <= 0) {
                this.disableChannel();
            }
        }
    }


    private int getFrequency() {
        return this.freqLowRegister | (this.freqHighRegister & 0b111) << 8;
    }


    private void triggerEvent() {
        this.enabled = true;
        this.volumeEnvelope.triggerEvent();
        if (this.sweep != null) {
            this.sweep.triggerEvent(this.getFrequency());
        }
    }


    private void disableChannel() {
        this.enabled = false;
    }


    public void setDutyPosition(int value) {
        this.dutyPosition = value;
    }


    private boolean isDacOn() {
        return (this.volumeEnvelope.readByte(this.volumeRegisterAddress) & 0b1111_1000) > 0;
    }


    private boolean isLengthTimerEnabled() {
        return (this.freqHighRegister & 0b100_0000) > 0;
    }


    public boolean isEnabled() {
        return this.enabled;
    }


    @Override
    public boolean acceptsAddress(int address) {
        return address == this.lengthDutyRegisterAddress || address == this.freqLowRegisterAddress || address == this.freqHighRegisterAddress
                || this.volumeEnvelope.acceptsAddress(address) || this.sweep != null && this.sweep.acceptsAddress(address);
    }


    @Override
    public int readByte(int address) {
        if (address == this.lengthDutyRegisterAddress) {
            return this.lengthDutyRegister | 0b0011_1111;
        }
        if (address == this.freqLowRegisterAddress) {
            return this.freqLowRegister;
        }
        if (address == this.freqHighRegisterAddress) {
            return this.freqHighRegister | 0b1011_1111;
        }
        if (this.volumeEnvelope.acceptsAddress(address)) {
            return this.volumeEnvelope.readByte(address);
        }
        if (this.sweep != null && this.sweep.acceptsAddress(address)) {
            return this.sweep.readByte(address);
        }

        throw new RuntimeException("Invalid address: " + Integer.toHexString(address));
    }


    @Override
    public void writeByte(int address, int value) {
        if (address == this.lengthDutyRegisterAddress) {
            this.lengthDutyRegister = value;
            this.lengthTimer = 64 - (value & 0b11_1111);
            return;
        }
        if (address == this.freqLowRegisterAddress) {
            this.freqLowRegister = value;
            return;
        }
        if (address == this.freqHighRegisterAddress) {
            this.freqHighRegister = value;
            if ((value & 0b1000_0000) > 0 && this.isDacOn()) {
                this.triggerEvent();
            }
            return;
        }
        if (this.volumeEnvelope.acceptsAddress(address)) {
            this.volumeEnvelope.writeByte(address, value);
            return;
        }
        if (this.sweep != null && this.sweep.acceptsAddress(address)) {
            this.sweep.writeByte(address, value);
            return;
        }

        throw new RuntimeException("Invalid address: " + Integer.toHexString(address));
    }

}
