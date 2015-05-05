package com.tealcube.java.vlo.generators;

import com.tealcube.java.vlo.blocks.BlockType;
import com.tealcube.java.vlo.dungeons.Dungeon;
import com.tealcube.java.vlo.populators.Populator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleGenerator implements Generator {

    private final double windiness;
    private final int passageSize;
    private final List<Populator> populators = new ArrayList<Populator>();

    public SimpleGenerator(double windiness, int passageSize) {
        this.windiness = windiness;
        this.passageSize = passageSize;
    }

    @Override public void generate(Dungeon dungeon, Random random) {
        int[][] grid = new int[dungeon.getWidth()][dungeon.getHeight()];
        int curX = random.nextInt(dungeon.getWidth());
        int curY = random.nextInt(dungeon.getHeight());
        grid[curX][curY] = 1;
        Direction currentDirection = Direction.values()[random.nextInt(4)];
        while (true) {
            if (random.nextDouble() < windiness) {
                currentDirection = Direction.values()[random.nextInt(4)];
                continue;
            }
            int nx = curX + currentDirection.cx * passageSize;
            int ny = curY + currentDirection.cy * passageSize;
            if (nx < dungeon.getWidth() - 1 && nx > 1 && ny < dungeon.getHeight() - 1 && ny > 1 && grid[nx][ny] == 0) {
                for (int i = 0; i < passageSize; i++) {
                    grid[nx - (currentDirection.cx * i)][ny - (currentDirection.cy * i)] = 1;
                }
                curX = nx;
                curY = ny;
                continue;
            }
            List<Direction> directionsToTry = new ArrayList<Direction>();
            for (Direction d : Direction.values()) {
                if (d == currentDirection) {
                    continue;
                }
                directionsToTry.add(d);
            }
            boolean found = false;
            while (!directionsToTry.isEmpty()) {
                Direction d = directionsToTry.get(random.nextInt(directionsToTry.size()));
                directionsToTry.remove(d);
                nx = curX + d.cx * passageSize;
                ny = curY + d.cy * passageSize;
                if (nx < dungeon.getWidth() - 1 && nx > 1 && ny < dungeon.getHeight() - 1 && ny > 1 && grid[nx][ny] == 0) {
                    currentDirection = d;
                    for (int i = 0; i < passageSize; i++) {
                        grid[nx - (d.cx * i)][ny - (d.cy * i)] = 1;
                    }
                    curX = nx;
                    curY = ny;
                    found = true;
                    break;
                }
            }
            if (!found) {
                break;
            }
        }

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                dungeon.setBlockType(x, y, grid[x][y] == 1 ? BlockType.AIR : BlockType.STONE);
            }
        }

        for (Populator populator : getPopulators()) {
            populator.populate(dungeon, random);
        }
    }

    @Override public List<Populator> getPopulators() {
        return populators;
    }

    enum Direction {
        NORTH(0, 1),
        EAST(1, 0),
        SOUTH(0, -1),
        WEST(-1, 0);

        final int cx;
        final int cy;

        Direction(int cx, int cy) {
            this.cx = cx;
            this.cy = cy;
        }
    }

}
