package de.pottgames.anemulator.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import de.pottgames.anemulator.Start;
import de.pottgames.anemulator.cpu.Register;
import de.pottgames.anemulator.cpu.Register.RegisterId;
import de.pottgames.anemulator.memory.MemoryBankController;

public class DebugInput implements InputProcessor {
    private final Register             register;
    private final MemoryBankController memory;
    private final Start                start;


    public DebugInput(Register register, MemoryBankController memory, Start start) {
        this.register = register;
        this.memory = memory;
        this.start = start;
    }


    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.F1) {
            System.out.println("F1");
            this.register.setPc(0x100);
            this.memory.setBooted();
            return true;
        }
        if (keycode == Input.Keys.F2) {
            this.start.nextStep(1);
            return true;
        }
        if (keycode == Input.Keys.F3) {
            this.start.nextStep(700000);
            return true;
        }
        if (keycode == Input.Keys.F4) {
            System.out.println("A: " + this.register.get(RegisterId.A));
            System.out.println("B: " + this.register.get(RegisterId.B));
            System.out.println("C: " + this.register.get(RegisterId.C));
            System.out.println("D: " + this.register.get(RegisterId.D));
            System.out.println("E: " + this.register.get(RegisterId.E));
            System.out.println("H: " + this.register.get(RegisterId.H));
            System.out.println("L: " + this.register.get(RegisterId.L));
            System.out.println("F: " + this.register.get(RegisterId.F));
            System.out.println("AF: " + this.register.get(RegisterId.AF));
            System.out.println("BC: " + this.register.get(RegisterId.BC));
            System.out.println("DE: " + this.register.get(RegisterId.DE));
            System.out.println("HL: " + this.register.get(RegisterId.HL));
            return true;
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
