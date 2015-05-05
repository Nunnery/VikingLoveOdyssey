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

        int startX = random.nextInt(width);
        int startY = random.nextInt(height);

        while (startX < 1 || startX >= (width - 1) || startY < 1 || startY >= (height - 1)) {
            startX = random.nextInt(width);
            startY = random.nextInt(height);
        }

        maz[startX][startY] = 1;
        Point startPoint = new Point(startX, startY, null);

        List<Point> frontier = new ArrayList<Point>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0 || x != 0 && y != 0) {
                    continue;
                }
                Point point = new Point(startPoint.x + x, startPoint.y + y, startPoint);
                if (point.x < 1 || point.x >= (width - 1) || point.y < 1 || point.y >= (height - 1)) {
                    continue;
                }
                frontier.add(point);
            }
        }

        Point lastPoint = null;

        while (!frontier.isEmpty()) {
            Point cur = frontier.remove(random.nextInt(frontier.size()));
            Point opp = cur.opposite();

            if (opp == null) {
                continue;
            }
            if (opp.x < 1 || opp.x >= (width - 1) || opp.y < 1 || opp.y >= (height - 1)) {
                continue;
            }
            if (maz[cur.x][cur.y] == 1 || maz[opp.x][opp.y] == 1) {
                continue;
            }
            maz[cur.x][cur.y] = 1;
            maz[opp.x][opp.y] = 1;

            lastPoint = opp;

            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (x == 0 && y == 0 || x != 0 && y != 0) {
                        continue;
                    }
                    Point point = new Point(opp.x + x, opp.y + y, opp);
                    if (point.x < 1 || point.x >= (width - 1) || point.y < 1 || point.y >= (height - 1)) {
                        continue;
                    }
                    frontier.add(point);
                }
            }
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (lastPoint != null && x == lastPoint.x && y == lastPoint.y) {
                    dungeon.setBlockType(x, y, BlockType.END);
                } else if (x == startPoint.x && y == startPoint.y) {
                    dungeon.setBlockType(x, y, BlockType.START);
                } else {
                    dungeon.setBlockType(x, y, maz[x][y] == 1 ? BlockType.AIR : BlockType.STONE);
                }
            }
        }

        for (Populator populator : getPopulators()) {
            populator.populate(dungeon, random);
        }
    }

    @Override public List<Populator> getPopulators() {
        return populators;
    }

    class Point {
        Integer x;
        Integer y;
        Point parent;

        Point(int x, int y, Point p) {
            this.x = x;
            this.y = y;
            this.parent = p;
        }

        Point opposite() {
            if (x.compareTo(parent.x) != 0) {
                return new Point(this.x + x.compareTo(parent.x), this.y, this);
            }
            if (y.compareTo(parent.y) != 0) {
                return new Point(this.x, this.y + y.compareTo(parent.y), this);
            }
            return null;
        }
    }

}
