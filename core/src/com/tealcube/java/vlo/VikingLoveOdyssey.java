package com.tealcube.java.vlo;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.tealcube.java.vlo.blocks.BlockTextureMap;
import com.tealcube.java.vlo.blocks.BlockType;
import com.tealcube.java.vlo.dungeons.Dungeon;
import com.tealcube.java.vlo.dungeons.DungeonView;
import com.tealcube.java.vlo.generators.Generator;
import com.tealcube.java.vlo.generators.PrimsGenerator;
import com.tealcube.java.vlo.populators.LoopPopulator;
import com.tealcube.java.vlo.populators.SparsenessPopulator;

import java.util.Random;

public class VikingLoveOdyssey extends ApplicationAdapter {

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 640;

    private DungeonView dungeonView;
    private Generator generator;
    private Random random;
    private Engine engine;
    private long lastUpdate = 0;

    @Override public void create() {
        for (BlockType blockType : BlockType.values()) {
            BlockTextureMap.getInstance().put(blockType, new Texture(Gdx.files.internal(blockType.getPath())));
        }

        engine = new Engine();

        random = new Random(System.currentTimeMillis());

        int dungeonWidth = GAME_WIDTH / DungeonView.TILE_WIDTH;
        int dungeonHeight = GAME_HEIGHT / DungeonView.TILE_HEIGHT;
        Dungeon dungeon = new Dungeon(dungeonWidth, dungeonHeight);

        generator = new PrimsGenerator();
        generator.getPopulators().add(new SparsenessPopulator(5));
        generator.getPopulators().add(new LoopPopulator(0.5D, 0.5D));
        generator.generate(dungeon, random);

        dungeonView = new DungeonView(dungeon);
    }

    @Override public void render() {
        if (lastUpdate == 0) {
            lastUpdate = System.currentTimeMillis();
        }
        engine.update(System.currentTimeMillis() - lastUpdate);
        lastUpdate = System.currentTimeMillis();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        dungeonView.render();
    }

    @Override public void dispose() {
        dungeonView.dispose();
        for (BlockType blockType : BlockType.values()) {
            BlockTextureMap.getInstance().get(blockType).dispose();
        }
    }

}
