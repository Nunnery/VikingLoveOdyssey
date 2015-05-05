package com.tealcube.java.vlo.dungeons;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tealcube.java.vlo.VikingLoveOdyssey;
import com.tealcube.java.vlo.blocks.BlockTextureMap;
import com.tealcube.java.vlo.blocks.BlockType;

public class DungeonView {

    public static final int TILE_WIDTH = 32, TILE_HEIGHT = 32;

    private final Dungeon dungeon;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;

    public DungeonView(Dungeon dungeon) {
        this.dungeon = dungeon;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, VikingLoveOdyssey.GAME_WIDTH, VikingLoveOdyssey.GAME_HEIGHT);
        this.batch = new SpriteBatch();
    }

    public void render() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                BlockType blockType = dungeon.getBlockType(x, y);
                Texture texture = BlockTextureMap.getInstance().get(blockType);
                batch.draw(texture, x * TILE_WIDTH, y * TILE_HEIGHT);
            }
        }
        batch.end();
    }

    public void dispose() {
        batch.dispose();
    }

}
