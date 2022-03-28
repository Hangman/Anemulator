package de.pottgames.anemulator.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.memory.MemoryBankController;

public class DebugInput implements InputProcessor {
    private final Register             register;
    private final MemoryBankController memory;


    public DebugInput(Register register, MemoryBankController memory) {
        this.register = register;
        this.memory = memory;
    }


    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.F1) {
            System.out.println("F1");
            this.register.pc = 0x100;
            this.memory.setBooted();
        }

        return false;
    }


    @Override
    public boolean keyUp(int keycode) {
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
