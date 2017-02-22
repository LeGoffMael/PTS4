package com.pts4.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pts4.game.states.GameStateManager;

public class PTS4 extends ApplicationAdapter {
	//La largeur de la fenêtre
	public static final int WIDTH = 800;
	//La hauteur de la fenêtre
	public static final int HEIGHT = 480;
	//Le titre de la fenêtre
	public static final String TITLE = "PTS4";

	private GameStateManager gsm;

	SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		gsm = new GameStateManager();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
