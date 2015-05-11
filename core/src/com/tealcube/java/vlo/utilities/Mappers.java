package com.tealcube.java.vlo.utilities;

import com.badlogic.ashley.core.ComponentMapper;
import com.tealcube.java.vlo.components.PositionComponent;
import com.tealcube.java.vlo.components.VelocityComponent;

public final class Mappers {

    private static final ComponentMapper<PositionComponent> POSITION_MAPPER = ComponentMapper.getFor(PositionComponent.class);
    private static final ComponentMapper<VelocityComponent> VELOCITY_MAPPER = ComponentMapper.getFor(VelocityComponent.class);

    private Mappers() {
        // do nothing
    }

    public static ComponentMapper<PositionComponent> getPositionMapper() {
        return POSITION_MAPPER;
    }

    public static ComponentMapper<VelocityComponent> getVelocityMapper() {
        return VELOCITY_MAPPER;
    }

}
