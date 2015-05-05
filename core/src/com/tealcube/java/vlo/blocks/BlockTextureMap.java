package com.tealcube.java.vlo.blocks;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

/**
 * BlockTextureMap is a singleton class that holds all block textures
 */
public class BlockTextureMap extends HashMap<BlockType, Texture> {

    private static BlockTextureMap instance;

    private BlockTextureMap() {
        // do nothing but call super
        super();
    }

    public static BlockTextureMap getInstance() {
        if (instance == null) {
            instance = new BlockTextureMap();
        }
        return instance;
    }

}
