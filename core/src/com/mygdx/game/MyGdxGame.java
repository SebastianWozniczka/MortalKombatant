package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.screens.GamePlay;
import com.mygdx.screens.LogoScreen;

public class MyGdxGame extends Game {
	public static short DESTROYED_BIT = 1;
	public static short PLAYER_BIT = 2;
	public static  short ENEMY_BIT = 4;
	public static  short GROUND_BIT = 8;
	public SpriteBatch batch;
	public static AssetManager manager;
	private boolean paused;


	@Override
	public void create () {
		batch = new SpriteBatch();

		manager = new AssetManager();
		manager.load("sounds/menu.mp3", Music.class);
		manager.load("sounds/round1.mp3", Music.class);
		manager.load("sounds/figth.mp3", Music.class);
		manager.finishLoading();
		setScreen(new LogoScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	public void setPaused ( boolean paused){
		this.paused = paused;
	}

	public boolean isPaused () {
		return paused;
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
