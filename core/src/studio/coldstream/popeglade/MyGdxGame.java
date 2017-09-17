package studio.coldstream.popeglade;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import studio.coldstream.popeglade.gamehelpers.AssetLoader;
import studio.coldstream.popeglade.screens.GameScreen;

public class MyGdxGame extends Game {
	
	@Override
	public void create () {
		Gdx.app.log("MyGdxGame", "created");
		AssetLoader.load();
		setScreen(new GameScreen());
	}
	
	@Override
	public void dispose () {
		super.dispose();
		AssetLoader.dispose();
	}
}
