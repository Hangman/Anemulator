package de.pottgames.anemulator.input;

import com.badlogic.gdx.Input;

public enum JoypadKey {
    DOWN(JoypadKeyType.DIRECTION, Input.Keys.DOWN, 3),
    UP(JoypadKeyType.DIRECTION, Input.Keys.UP, 2),
    LEFT(JoypadKeyType.DIRECTION, Input.Keys.LEFT, 1),
    RIGHT(JoypadKeyType.DIRECTION, Input.Keys.RIGHT, 0),
    START(JoypadKeyType.ACTION, Input.Keys.ENTER, 3),
    SELECT(JoypadKeyType.ACTION, Input.Keys.SPACE, 2),
    B(JoypadKeyType.ACTION, Input.Keys.S, 1),
    A(JoypadKeyType.ACTION, Input.Keys.A, 0);


    public final JoypadKeyType type;
    public int                 key;
    public final int           bitnum;


    JoypadKey(JoypadKeyType type, int key, int bitnum) {
        this.type = type;
        this.key = key;
        this.bitnum = bitnum;
    }

}