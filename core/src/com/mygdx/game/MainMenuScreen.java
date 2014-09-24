package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainMenuScreen implements Screen{

	MyGdxGame game;
	OrthographicCamera camera;
	
	public MainMenuScreen(MyGdxGame game) {
		this.game = game;
		
		camera = new OrthographicCamera(800, 480);
	}
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
	      Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	        camera.update();
	        game.batch.setProjectionMatrix(camera.combined);

	        game.batch.begin();
	        game.font.draw(game.batch, "Welcome to Drop!!! ", 100, 150);
	        game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
	        game.batch.end();

	        if (Gdx.input.isTouched()) {
	            game.setScreen(new ReferenceScreen(game));
	            dispose();
	        }
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}


}