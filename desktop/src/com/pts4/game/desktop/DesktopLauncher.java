package com.pts4.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pts4.game.PTS4;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = PTS4.WIDTH;
		config.height = PTS4.HEIGHT;
		config.title = PTS4.TITLE;
		new LwjglApplication(new PTS4(), config);
	}
}
