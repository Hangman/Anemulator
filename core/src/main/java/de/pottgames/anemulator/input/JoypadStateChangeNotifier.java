package de.pottgames.anemulator.input;

public interface JoypadStateChangeNotifier {

    void addListener(JoypadStateChangeListener listener);


    void removeListener(JoypadStateChangeListener listener);

}
