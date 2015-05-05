package com.tealcube.java.vlo.blocks;

public enum BlockType {
    AIR("blocks/air.png"),
    STONE("blocks/stone.png"),
    START("blocks/start.png"),
    END("blocks/end.png");

    private final String path;

    BlockType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
