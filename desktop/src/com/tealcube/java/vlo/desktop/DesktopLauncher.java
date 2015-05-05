package com.tealcube.java.vlo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tealcube.java.vlo.VikingLoveOdyssey;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = VikingLoveOdyssey.GAME_WIDTH;
        config.height = VikingLoveOdyssey.GAME_HEIGHT;
		new LwjglApplication(new VikingLoveOdyssey(), config);
	}
}
