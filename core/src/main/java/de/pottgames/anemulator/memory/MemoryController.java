package de.pottgames.anemulator.memory;

public interface MemoryController {

    int read8Bit(int address);


    int read16Bit(int address);


    void write(int address, int value);


    boolean isBootstrap();

}
