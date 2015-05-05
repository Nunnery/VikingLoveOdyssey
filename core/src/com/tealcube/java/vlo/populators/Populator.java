package com.tealcube.java.vlo.populators;

import com.tealcube.java.vlo.dungeons.Dungeon;

import java.util.Random;

public interface Populator {

    void populate(Dungeon dungeon, Random random);

}
