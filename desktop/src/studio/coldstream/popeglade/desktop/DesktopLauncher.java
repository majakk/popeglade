package studio.coldstream.popeglade.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import studio.coldstream.popeglade.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 970;
		config.width = 2092;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
