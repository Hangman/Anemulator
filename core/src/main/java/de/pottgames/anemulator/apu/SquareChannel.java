package de.pottgames.anemulator.apu;

import de.pottgames.anemulator.memory.Memory;

public class SquareChannel implements Memory {
    private final VolumeEnvelope volumeEnvelope;
    private SweepController      sweepController;
    private int                  frequencyTimer;
    private int                  dutyPosition;
    private final int            lengthDutyRegisterAddress;
    private int                  lengthDutyRegister;
    private final int            freqLowRegisterAddress;
    private final int            freqHighRegisterAddress;
    private int                  freqLowRegister;
    private int                  freqHighRegister;
    private float[]              samples = new float[2];


    public SquareChannel(int lengthRegisterAddress, int volumeRegisterAddress, int sweepRegisterAddress, int frequencyLowDataRegisterAddress,
            int frequencyHighDataRegisterAddress) {
        this.lengthDutyRegisterAddress = lengthRegisterAddress;
        this.freqLowRegisterAddress = frequencyLowDataRegisterAddress;
        this.freqHighRegisterAddress = frequencyHighDataRegisterAddress;
        this.volumeEnvelope = new VolumeEnvelope(volumeRegisterAddress);
        if (sweepRegisterAddress > 0) {
            this.sweepController = new SweepController(sweepRegisterAddress);
        }
    }


    public float[] step(boolean stepLength, boolean stepEnvelope, boolean stepSweep) {
        final float[] duty = APU.WAVE_DUTY[this.lengthDutyRegister >>> 6];
        final int frequency = this.freqLowRegister | (this.freqHighRegister & 0b111) << 8;

        this.frequencyTimer--;
        if (this.frequencyTimer <= 0) {
            this.frequencyTimer = (2048 - frequency) * 4;
            this.dutyPosition++;
            if (this.dutyPosition > 7) {
                this.dutyPosition = 0;
            }
        }

        if (stepLength) {
            this.stepLength();
        }
        if (stepEnvelope) {
            this.volumeEnvelope.step();
        }
        if (stepSweep && this.sweepController != null) {
            this.sweepController.step();
        }

        this.samples[0] = duty[this.dutyPosition];
        this.samples[1] = duty[this.dutyPosition];
        this.volumeEnvelope.modifySamples(this.samples);

        return this.samples;
    }


    private void stepLength() {

    }


    @Override
    public boolean acceptsAddress(int address) {
        return address == this.lengthDutyRegisterAddress || address == this.freqLowRegisterAddress || address == this.freqHighRegisterAddress
                || this.volumeEnvelope.acceptsAddress(address) || this.sweepController != null && this.sweepController.acceptsAddress(address);
    }


    @Override
    public int readByte(int address) {
        if (address == this.lengthDutyRegisterAddress) {
            return this.lengthDutyRegister;
        }
        if (address == this.freqLowRegisterAddress) {
            return this.freqLowRegister;
        }
        if (address == this.freqHighRegisterAddress) {
            return this.freqHighRegister;
        }
        if (this.volumeEnvelope.acceptsAddress(address)) {
            return this.volumeEnvelope.readByte(address);
        }
        if (this.sweepController != null && this.sweepController.acceptsAddress(address)) {
            return this.sweepController.readByte(address);
        }

        System.out.println(Integer.toHexString(address));

        throw new RuntimeException("Invalid address");
    }


    private void triggerEvent() {
        this.volumeEnvelope.triggerEvent();
    }


    @Override
    public void writeByte(int address, int value) {
        if (address == this.lengthDutyRegisterAddress) {
            this.lengthDutyRegister = value;
            return;
        }
        if (address == this.freqLowRegisterAddress) {
            this.freqLowRegister = value;
            return;
        }
        if (address == this.freqHighRegisterAddress) {
            this.freqHighRegister = value;
            if ((value & 0b10000000) > 0) {
                this.triggerEvent();
            }
            return;
        }
        if (this.volumeEnvelope.acceptsAddress(address)) {
            this.volumeEnvelope.writeByte(address, value);
            return;
        }
        if (this.sweepController != null && this.sweepController.acceptsAddress(address)) {
            this.sweepController.writeByte(address, value);
            return;
        }

        throw new RuntimeException("Invalid address");
    }

}
