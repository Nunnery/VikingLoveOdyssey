package com.tealcube.java.vlo.dungeons;

import com.tealcube.java.vlo.blocks.BlockType;

public class Dungeon {

    private final int width;
    private final int height;
    private final BlockType[][] blocks;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.blocks = new BlockType[width][height];
        for (int x = 0; x < this.blocks.length; x++) {
            for (int y = 0; y < this.blocks[x].length; y++) {
                this.blocks[x][y] = BlockType.STONE;
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setBlockType(int x, int y, BlockType type) {
        if (x < width || x >= 0 || y < height || y >= 0) {
            blocks[x][y] = type;
        }
    }

    public BlockType getBlockType(int x, int y) {
        return (x <= width || x >= 0 || y <= height || y >= 0) ? blocks[x][y] : BlockType.STONE;
    }

}
