package de.pottgames.anemulator.input;

@FunctionalInterface
public interface JoypadStateChangeListener {

    void onJoypadStateChange(JoypadKey key, boolean pressed);

}
