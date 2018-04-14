package studio.coldstream.popeglade;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import studio.coldstream.popeglade.gamehelpers.AssetLoader;
import studio.coldstream.popeglade.screens.MainGameScreen;

public class MyGdxGame extends Game {
	private static final String TAG = MyGdxGame.class.getSimpleName();

	private static MainGameScreen mainGameScreen;

	@Override
	public void create () {
		Gdx.app.log(TAG, "Created");

		//AssetLoader.loadPointer();
		mainGameScreen = new MainGameScreen();
		setScreen(mainGameScreen);
	}
	
	@Override
	public void dispose () {
		super.dispose();
		mainGameScreen.dispose();
		AssetLoader.dispose();
	}
}
