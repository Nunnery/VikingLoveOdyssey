package com.tealcube.java.vlo.components;

import com.badlogic.ashley.core.Component;

public class PositionComponent extends Component {

    private int x = 0, y = 0;

    public int getX() {
        return x;
    }

    public PositionComponent setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public PositionComponent setY(int y) {
        this.y = y;
        return this;
    }

}
