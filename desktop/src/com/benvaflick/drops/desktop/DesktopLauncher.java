package com.benvaflick.drops.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.benvaflick.drops.Drop;
import com.benvaflick.drops.GameScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Drops";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new Drop(), config);
	}
}
