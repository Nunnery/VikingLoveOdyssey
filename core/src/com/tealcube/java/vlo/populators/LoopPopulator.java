package com.tealcube.java.vlo.populators;

import com.tealcube.java.vlo.blocks.BlockType;
import com.tealcube.java.vlo.dungeons.Dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LoopPopulator implements Populator {

    private final double deadEndRemovePercentage;
    private final double windiness;

    public LoopPopulator(double deadEndRemovePercentage, double windiness) {
        this.deadEndRemovePercentage = deadEndRemovePercentage;
        this.windiness = windiness;
    }

    @Override public void populate(Dungeon dungeon, Random random) {
        int width = dungeon.getWidth();
        int height = dungeon.getHeight();

        // create a dead end grid
        boolean[][] deadEnds = new boolean[width][height];
        for (int x = 0; x < deadEnds.length; x++) {
            for (int y = 0; y < deadEnds[x].length; y++) {
                deadEnds[x][y] = false;
                if (dungeon.getBlockType(x, y) == BlockType.STONE) {
                    continue;
                }
                int foundAir = 0;
                for (int xx = -1; xx <= 1; xx++) {
                    for (int yy = -1; yy <= 1; yy++) {
                        if (xx == 0 && yy == 0 || xx != 0 && yy != 0) {
                            continue;
                        }
                        switch (dungeon.getBlockType(x + xx, y + yy)) {
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
                // if more than one air has been found, then it's not a dead end
                if (foundAir > 1) {
                    continue;
                }
                deadEnds[x][y] = true;
            }
        }

        // create a corridor grid
        // 0 - not corridor
        // 1 - corridor
        boolean[][] grid = new boolean[width][height];
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                BlockType bt = dungeon.getBlockType(x, y);
                switch (bt) {
                    case STONE:
                        grid[x][y] = false;
                        break;
                    default:
                        grid[x][y] = !deadEnds[x][y];
                        break;
                }
            }
        }

        for (int x = 0; x < deadEnds.length; x++) {
            for (int y = 0; y < deadEnds[x].length; y++) {
                // if the current cell isn't a dead end, we don't care
                if (!deadEnds[x][y]) {
                    continue;
                }
                // if random number is higher than chance, don't remove
                if (random.nextDouble() > deadEndRemovePercentage) {
                    continue;
                }

                int curX = x;
                int curY = y;
                Direction curDir = null;

                while (true) {
                    List<Direction> validDirections = new ArrayList<Direction>();
                    int nx = curX + Direction.NORTH.cx;
                    int ny = curY + Direction.NORTH.cy;
                    int ex = curX + Direction.EAST.cx;
                    int ey = curY + Direction.EAST.cy;
                    int sx = curX + Direction.SOUTH.cx;
                    int sy = curY + Direction.SOUTH.cy;
                    int wx = curX + Direction.WEST.cx;
                    int wy = curY + Direction.WEST.cy;
                    if (nx >= 0 && nx < width && ny >= 0 && ny < height)
                        validDirections.add(Direction.NORTH);
                    if (ex >= 0 && ex < width && ey >= 0 && ey < height)
                        validDirections.add(Direction.EAST);
                    if (sx >= 0 && sx < width && sy >= 0 && sy < height)
                        validDirections.add(Direction.SOUTH);
                    if (wx >= 0 && wx < width && wy >= 0 && wy < height)
                        validDirections.add(Direction.WEST);

                    if (validDirections.isEmpty()) {
                        break;
                    }

                    String valid = validDirections.toString();

                    if (curDir == null || !validDirections.contains(curDir) || random.nextDouble() <= windiness) {
                        curDir = validDirections.get(random.nextInt(validDirections.size()));
                    }
                    curX += curDir.cx;
                    curY += curDir.cy;
                    if (grid[curX][curY]) {
                        dungeon.setBlockType(curX, curY, BlockType.AIR);
                        break;
                    }
                    if (!deadEnds[curX][curY]) {
                        dungeon.setBlockType(curX, curY, BlockType.AIR);
                        deadEnds[curX][curY] = true;
                    } else {
                        break;
                    }
                }
            }
        }
    }

    enum Direction {
        NORTH(0, 1),
        EAST(1, 0),
        SOUTH(0, -1),
        WEST(-1, 0);

        final int cx;
        final int cy;

        Direction(int i, int i1) {
            this.cx = i;
            this.cy = i1;
        }
    }
}
