package com.tealcube.java.vlo.populators;

import com.tealcube.java.vlo.blocks.BlockType;
import com.tealcube.java.vlo.dungeons.Dungeon;

import java.util.Random;

public class SparsenessPopulator implements Populator {

    private final int sparseness;

    public SparsenessPopulator(int sparseness) {
        this.sparseness = sparseness;
    }

    @Override public void populate(Dungeon dungeon, Random random) {
        for (int i = 0; i < sparseness; i++) {
            // create a copy of the dungeon's grid
            BlockType[][] grid = new BlockType[dungeon.getWidth()][dungeon.getHeight()];
            for (int x = 0; x < grid.length; x++) {
                for (int y = 0; y < grid[x].length; y++) {
                    grid[x][y] = dungeon.getBlockType(x, y);
                }
            }

            // fill in any dead ends in the actual dungeon, not in the copy
            // we are going to check in the copy, though, so it's a little confusing
            for (int x = 0; x < grid.length; x++) {
                for (int y = 0; y < grid[x].length; y++) {
                    BlockType fromGrid = grid[x][y];
                    // if the current cell isn't air, we don't care
                    if (fromGrid != BlockType.AIR) {
                        continue;
                    }
                    // check neighbors
                    int foundAir = 0;
                    for (int xx = -1; xx <= 1; xx++) {
                        for (int yy = -1; yy <= 1; yy++) {
                            if (xx == 0 && yy == 0 || xx != 0 && yy != 0) {
                                continue;
                            }
                            switch (grid[x + xx][y + yy]) {
                                case AIR:
                                case START:
                                case END:
                                    foundAir++;
                                    break;
                                case STONE:
                                    break;
                            }
                        }
                    }

                    if (foundAir >= 2) {
                        continue;
                    }
                    dungeon.setBlockType(x, y, BlockType.STONE);
                }
            }
        }
    }
}
