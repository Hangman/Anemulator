package de.pottgames.anemulator.apu;

import com.badlogic.gdx.utils.Disposable;

import de.pottgames.anemulator.memory.Memory;
import de.pottgames.tuningfork.PcmFormat;
import de.pottgames.tuningfork.PcmSoundSource;

public class SquareChannel implements Memory, Disposable {
    private final PcmSoundSource      soundSource;
    private final int                 controlRegisterAddress;
    private final FrequencyController frequencyController;
    private final VolumeController    volumeController;
    private final LengthController    lengthController;
    private final SweepController     sweepController;


    public SquareChannel(int controlRegisterAddress, int frequencyRegisterAddress, int volumeRegisterAddress, int lengthRegisterAddress,
            int sweepRegisterAddress) {
        this.controlRegisterAddress = controlRegisterAddress;
        this.frequencyController = new FrequencyController(frequencyRegisterAddress);
        this.volumeController = new VolumeController(volumeRegisterAddress);
        this.lengthController = new LengthController(lengthRegisterAddress);
        if (sweepRegisterAddress > 0) {
            this.sweepController = new SweepController(sweepRegisterAddress);
        } else {
            this.sweepController = null;
        }
        this.soundSource = new PcmSoundSource(48000, PcmFormat.STEREO_16_BIT);
    }


    @Override
    public boolean acceptsAddress(int address) {
        return address == this.controlRegisterAddress || this.frequencyController.acceptsAddress(address) || this.volumeController.acceptsAddress(address)
                || this.lengthController.acceptsAddress(address) || this.sweepController != null && this.sweepController.acceptsAddress(address);
    }


    @Override
    public int readByte(int address) {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public int readWord(int address) {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public void writeByte(int address, int value) {
        // TODO Auto-generated method stub

    }


    @Override
    public void dispose() {
        this.soundSource.dispose();
    }

}
