package de.pottgames.anemulator.apu;

import de.pottgames.anemulator.memory.Memory;

public class Sweep implements Memory {
    private int            shadowFrequency;
    private int            timer;
    private int            period;
    private boolean        enabled;
    private int            frequency;
    private int            shift;
    private boolean        decrementing;
    private final Runnable disableChannel;


    public Sweep(Runnable disableChannel) {
        this.disableChannel = disableChannel;
    }


    public void step() {
        if (this.timer > 0) {
            this.timer -= 1;
        }

        if (this.timer == 0) {
            this.timer = this.period > 0 ? this.period : 8;

            if (this.enabled && this.period > 0) {
                final int newFrequency = this.calculateFrequency();

                if (newFrequency <= 2047 && this.shift > 0) {
                    this.frequency = newFrequency;
                    this.shadowFrequency = newFrequency;

                    this.calculateFrequency();
                }
            }
        }
    }


    private int calculateFrequency() {
        int newFrequency = this.shadowFrequency >>> this.shift;

        if (this.decrementing) {
            newFrequency = this.shadowFrequency - newFrequency;
        } else {
            newFrequency = this.shadowFrequency + newFrequency;
        }

        // OVERFLOW CHECK
        if (newFrequency > 2047) {
            this.disableChannel.run();
        }

        return newFrequency;
    }


    public int getFrequency(int unsweepedFrequency) {
        return this.enabled ? this.frequency : unsweepedFrequency;
    }


    public void triggerEvent(int frequency) {
        this.shadowFrequency = frequency;
        this.timer = this.period > 0 ? this.period : 8;
        this.enabled = this.period > 0 || this.shift > 0;
        if (this.shift > 0) {
            this.calculateFrequency();
        }
    }


    @Override
    public boolean acceptsAddress(int address) {
        return address == Memory.NR10;
    }


    @Override
    public int readByte(int address) {
        if (address == Memory.NR10) {
            return this.period << 4 | (this.decrementing ? 0b1000 : 0) | this.shift | 0b10000000;
        }

        throw new RuntimeException("Invalid address");
    }


    @Override
    public void writeByte(int address, int value) {
        if (address != Memory.NR10) {
            throw new RuntimeException("Invalid address");
        }

        this.decrementing = (value & 0b1000) > 0;
        this.period = (value & 0b1110000) >>> 4;
        this.shift = value & 0b111;
    }

}
