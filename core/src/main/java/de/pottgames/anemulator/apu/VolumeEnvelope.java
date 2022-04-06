package de.pottgames.anemulator.apu;

import de.pottgames.anemulator.memory.Memory;

public class VolumeEnvelope implements Memory {
    private int       period;
    private int       periodTimer;
    private int       volume;
    private boolean   upwards;
    private final int register[] = new int[1];
    private final int registerAddress;


    public VolumeEnvelope(int registerAddress) {
        this.registerAddress = registerAddress;
    }


    public void step() {
        if (this.period != 0) {
            if (this.periodTimer > 0) {
                this.periodTimer -= 1;
            }

            if (this.periodTimer == 0) {
                this.periodTimer = this.period;
                if (this.volume < 0xF && this.upwards || this.volume > 0x0 && !this.upwards) {
                    if (this.upwards) {
                        this.volume += 1;
                    } else {
                        this.volume -= 1;
                    }
                }
            }
        }
    }


    public void modifySamples(float[] samples) {
        final float factor = (float) this.volume / (float) 0xF;
        samples[0] *= factor;
        samples[1] *= factor;
    }


    public void triggerEvent() {
        this.periodTimer = this.register[0] & 0b111;
        this.volume = this.register[0] >>> 4;
    }


    @Override
    public boolean acceptsAddress(int address) {
        return address == this.registerAddress;
    }


    @Override
    public int readByte(int address) {
        return this.register[address - this.registerAddress];
    }


    @Override
    public void writeByte(int address, int value) {
        this.register[address - this.registerAddress] = value;
        this.upwards = (this.register[0] & 0b1000) > 0;
        this.period = this.register[0] & 0b111;
    }

}
