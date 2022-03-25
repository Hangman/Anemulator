package de.pottgames.anemulator.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;

public class KeyboardInput implements InputProcessor, JoypadStateChangeNotifier {
    private int upKeycode     = Input.Keys.UP;
    private int downKeycode   = Input.Keys.DOWN;
    private int leftKeycode   = Input.Keys.LEFT;
    private int rightKeycode  = Input.Keys.RIGHT;
    private int startKeycode  = Input.Keys.ENTER;
    private int selectKeycode = Input.Keys.SPACE;
    private int aKeycode      = Input.Keys.A;
    private int bKeycode      = Input.Keys.S;

    private boolean[]                              buttonPressed = new boolean[JoypadKey.values().length];
    private final Array<JoypadStateChangeListener> listeners     = new Array<>();


    @Override
    public boolean keyDown(int keycode) {
        final JoypadKey key = this.getJoypadKey(keycode);
        if (key != null) {
            final int keyIndex = key.getIndex();
            if (this.buttonPressed[keyIndex] == false) {
                this.buttonPressed[keyIndex] = true;
                for (final JoypadStateChangeListener listener : this.listeners) {
                    listener.onJoypadStateChange(key, true);
                }
            }
            return true;
        }

        return false;
    }


    @Override
    public boolean keyUp(int keycode) {
        final JoypadKey key = this.getJoypadKey(keycode);
        if (key != null) {
            final int keyIndex = key.getIndex();
            if (this.buttonPressed[keyIndex] == true) {
                this.buttonPressed[keyIndex] = false;
                for (final JoypadStateChangeListener listener : this.listeners) {
                    listener.onJoypadStateChange(key, false);
                }
            }
            return true;
        }

        return false;
    }


    @Override
    public boolean keyTyped(char character) {
        return false;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }


    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }


    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }


    private JoypadKey getJoypadKey(int keycode) {
        if (keycode == this.upKeycode) {
            return JoypadKey.UP;
        }
        if (keycode == this.downKeycode) {
            return JoypadKey.DOWN;
        }
        if (keycode == this.leftKeycode) {
            return JoypadKey.LEFT;
        }
        if (keycode == this.rightKeycode) {
            return JoypadKey.RIGHT;
        }
        if (keycode == this.startKeycode) {
            return JoypadKey.START;
        }
        if (keycode == this.selectKeycode) {
            return JoypadKey.SELECT;
        }
        if (keycode == this.aKeycode) {
            return JoypadKey.A;
        }
        if (keycode == this.bKeycode) {
            return JoypadKey.B;
        }
        return null;
    }


    @Override
    public void addListener(JoypadStateChangeListener listener) {
        if (!this.listeners.contains(listener, true)) {
            this.listeners.add(listener);
        }
    }


    @Override
    public void removeListener(JoypadStateChangeListener listener) {
        this.listeners.removeValue(listener, true);
    }

}
