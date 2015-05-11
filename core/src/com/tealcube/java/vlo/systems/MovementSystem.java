package com.tealcube.java.vlo.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.tealcube.java.vlo.components.PositionComponent;
import com.tealcube.java.vlo.components.VelocityComponent;
import com.tealcube.java.vlo.utilities.Mappers;

public class MovementSystem extends IteratingSystem {

    public MovementSystem() {
        super(Family.all(PositionComponent.class, VelocityComponent.class).get());
    }

    @Override protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent pc = Mappers.getPositionMapper().get(entity);
        VelocityComponent vc = Mappers.getVelocityMapper().get(entity);

        pc.setX((int) (pc.getX() + (vc.getX() * deltaTime)));
        pc.setY((int) (pc.getY() + (vc.getY() * deltaTime)));
    }

}
