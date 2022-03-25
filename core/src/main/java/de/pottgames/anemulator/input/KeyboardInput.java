package de.pottgames.anemulator.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.ObjectMap;

public class KeyboardInput implements InputProcessor, InputController {
    private final ObjectMap<JoypadKey, Boolean> keydownMap = new ObjectMap<>();


    public KeyboardInput() {
        for (final JoypadKey key : JoypadKey.values()) {
            this.keydownMap.put(key, false);
        }
    }


    @Override
    public boolean isButtonPressed(JoypadKey key) {
        return this.keydownMap.get(key);
    }


    @Override
    public boolean keyDown(int keycode) {
        for (final JoypadKey key : JoypadKey.values()) {
            if (key.key == keycode) {
                this.keydownMap.put(key, true);
                return true;
            }
        }

        return false;
    }


    @Override
    public boolean keyUp(int keycode) {
        for (final JoypadKey key : JoypadKey.values()) {
            if (key.key == keycode) {
                this.keydownMap.put(key, false);
                return true;
            }
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

}
