package de.pottgames.anemulator.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import de.pottgames.anemulator.Start;

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        Lwjgl3Launcher.createApplication();
    }


    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new Start(), Lwjgl3Launcher.getDefaultConfiguration());
    }


    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        final Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Anemulator");
        configuration.useVsync(false);
        configuration.setForegroundFPS(0);
        configuration.setWindowedMode(640, 480);
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        configuration.disableAudio(true);
        return configuration;
    }

}