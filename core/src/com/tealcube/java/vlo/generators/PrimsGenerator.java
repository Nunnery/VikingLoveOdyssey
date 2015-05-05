package com.tealcube.java.vlo.generators;

import com.tealcube.java.vlo.blocks.BlockType;
import com.tealcube.java.vlo.dungeons.Dungeon;
import com.tealcube.java.vlo.populators.Populator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrimsGenerator implements Generator {

    private final List<Populator> populators = new ArrayList<Populator>();

    @Override public void generate(Dungeon dungeon, Random random) {
        int width = dungeon.getWidth();
        int height = dungeon.getHeight();
        int[][] maz = new int[width][height];

        int curX = random.nextInt(width);
        int curY = random.nextInt(height);
        maz[curX][curY] = 1;

        Point startPoint = new Point(curX, curY, null);
        List<Point> frontier = new ArrayList<Point>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                // only look at non-diagonal neighbors
                if (x == 0 && y == 0 || x != 0 && y != 0 || x < 0 || x >= width || y < 0 || y >= height) {
                    continue;
                }
                if (maz[startPoint.r + x][startPoint.c + y] == 1) {
                    continue;
                }
                frontier.add(new Point(startPoint.r + x, startPoint.c + y, startPoint));
            }
        }

        Point endPoint = null;
        while (!frontier.isEmpty()) {
            Point current = frontier.remove(random.nextInt(frontier.size()));
            Point opposite = current.opposite();
            if (maz[current.r][current.c] == 0 && maz[opposite.r][opposite.c] == 0) {
                maz[current.r][current.c] = 1;
                maz[opposite.r][opposite.c] = 1;
                endPoint = opposite;

                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        // only look at non-diagonal neighbors
                        if (x == 0 && y == 0 || x != 0 && y != 0 || x < 0 || x >= width || y < 0 || y >= height) {
                            continue;
                        }
                        if (maz[opposite.r + x][opposite.c + y] == 1) {
                            continue;
                        }
                        frontier.add(new Point(opposite.r + x, startPoint.c + y, opposite));
                    }
                }
            }
        }

        for (int x = 0; x < maz.length; x++) {
            for (int y = 0; y < maz[x].length; y++) {
                if (x == startPoint.c && y == startPoint.r) {
                    dungeon.setBlockType(x, y, BlockType.START);
                } else if (endPoint != null && x == endPoint.c && y == endPoint.r) {
                    dungeon.setBlockType(x, y, BlockType.END);
                } else {
                    dungeon.setBlockType(x, y, maz[x][y] == 1 ? BlockType.AIR : BlockType.STONE);
                }
            }
        }
    }

    @Override public List<Populator> getPopulators() {
        return populators;
    }

    class Point {
        int r;
        int c;
        Point parent;

        Point(int x, int y, Point p) {
            this.r = x;
            this.c = y;
            this.parent = p;
        }

        Point opposite() {
            if (Integer.compare(r, parent.r) != 0) {
                return new Point(this.r + Integer.compare(r, parent.r), this.c, this);
            }
            if (Integer.compare(c, parent.c) != 0) {
                return new Point(this.r, this.c + Integer.compare(c, parent.c), this);
            }
            return null;
        }
    }

}
