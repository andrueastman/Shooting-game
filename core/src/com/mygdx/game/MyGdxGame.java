package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game {

	public SpriteBatch batch;
	public BitmapFont font;
	public int score;
	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		score =0;
		// TODO Auto-generated method stub
		this.setScreen(new MainMenuScreen(this));
	}
	public void render() {
		super.render();
	}
	public void dispose() {
		
	}
}
