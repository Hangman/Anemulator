package de.pottgames.anemulator.test;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import de.pottgames.anemulator.Gameboy;
import de.pottgames.anemulator.memory.Memory;

public class MemoryAcceptanceTest {

    @ParameterizedTest
    @ValueSource(strings = { "../assets/Dr. Mario (World).gb", "../assets/Tetris (World) (Rev A).gb", "../assets/Batman - The Video Game (World).gb",
            "../assets/Donkey Kong Land (USA, Europe) (SGB Enhanced).gb" })
    public void checkDuplicates(String path) {
        final Gameboy gameboy = new Gameboy(path, null);
        final List<Memory> memoryList = gameboy.getMemoryList();

        for (int i = 0; i <= 0xFFFF; i++) {
            Memory accepted = null;
            for (final Memory memory : memoryList) {
                final boolean accepts = memory.acceptsAddress(i);
                if (accepted != null && accepts) {
                    Assertions.fail("Duplicate acceptance of address: " + Integer.toHexString(i) + " by " + accepted + " and " + memory);
                } else if (accepts) {
                    accepted = memory;
                }
            }
            if (accepted == null) {
                Assertions.fail("Address is missing an acceptor: " + Integer.toHexString(i));
            }
        }
    }

}
