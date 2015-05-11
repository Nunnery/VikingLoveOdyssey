package com.tealcube.java.vlo.components;

import com.badlogic.ashley.core.Component;

public class VelocityComponent extends Component {

    private int x = 0, y = 0;

    public int getX() {
        return x;
    }

    public VelocityComponent setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public VelocityComponent setY(int y) {
        this.y = y;
        return this;
    }

}
