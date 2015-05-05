package com.tealcube.java.vlo.generators;

import com.tealcube.java.vlo.dungeons.Dungeon;
import com.tealcube.java.vlo.populators.Populator;

import java.util.List;
import java.util.Random;

public interface Generator {

    void generate(Dungeon dungeon, Random random);

    List<Populator> getPopulators();

}
