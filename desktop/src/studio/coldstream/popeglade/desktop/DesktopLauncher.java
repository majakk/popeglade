package studio.coldstream.popeglade.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import studio.coldstream.popeglade.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "The PopeGlade Gnome";
		config.useGL30 = false;
		config.height = 640;
		config.width = 960;
		config.addIcon("android/assets/gfx/box.png", Files.FileType.Internal); //Main Window Icon

		Application app = new LwjglApplication(new MyGdxGame(), config);
		Gdx.app = app;

		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		//Gdx.app.setLogLevel(Application.LOG_INFO);
		//Gdx.app.setLogLevel(Application.LOG_ERROR);
		//Gdx.app.setLogLevel(Application.LOG_NONE);
	}
}
