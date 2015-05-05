package com.tealcube.java.vlo.populators;

import com.tealcube.java.vlo.blocks.BlockType;
import com.tealcube.java.vlo.dungeons.Dungeon;

import java.util.Random;

public class RoomPopulator implements Populator {

    private final int rooms;

    public RoomPopulator(int rooms) {
        this.rooms = rooms;
    }

    @Override public void populate(Dungeon dungeon, Random random) {
        int curRooms = 0;
        while (curRooms < rooms) {
            int curX = random.nextInt(dungeon.getWidth());
            int curY = random.nextInt(dungeon.getHeight());
            while (dungeon.getBlockType(curX, curY) == BlockType.STONE) {
                curX = random.nextInt(dungeon.getWidth());
                curY = random.nextInt(dungeon.getHeight());
            }
            boolean canPopulate = true;
            for (int x = -1; x <= 1; x++) {
                if (!canPopulate) {
                    break;
                }
                for (int y = -1; y <= 1; y++) {
                    if (!canPopulate) {
                        break;
                    }
                    int nx = curX + x;
                    int ny = curY + y;
                    // I'm super lazy, so let's do this
                    if (!(nx < dungeon.getWidth() - 1 && nx > 1 && ny < dungeon.getHeight() - 1 && ny > 1)) {
                        canPopulate = false;
                    }
                }
            }
            if (!canPopulate) {
                continue;
            }
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    int nx = curX + x;
                    int ny = curY + y;
                    dungeon.setBlockType(nx, ny, BlockType.AIR);
                }
            }
            curRooms++;
        }
    }

}
