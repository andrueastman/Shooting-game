package com.mygdx.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MyGdxGame;

public class AnimatedCharacter {

	int NUM_ROWS, NUM_COLS;
	public int px, py;
	TextureRegion [] character;
	TextureRegion currentCharacter;
	MyGdxGame game;
	public float stateTime;
	int width, height;
	Animation animation;

	public AnimatedCharacter(MyGdxGame game, Texture tx, int rows, int cols, float frameDuration, int px, int py) {
	    this.game = game;
		this.px = px;
	    this.py = py;
		TextureRegion [][] tmp = TextureRegion.split(tx,tx.getWidth()/ rows, tx.getHeight()/cols);
	      character = new TextureRegion[rows * cols];
	      int index = 0;
	      for (int i = 0; i < cols; i++) {
	          for (int j = 0; j <rows ; j++) {
	              character[index++] = tmp[i][j];
	          }
	      }
	      width = character[0].getRegionWidth();
	      height = character[0].getRegionHeight();
	      animation = new Animation(frameDuration, character);
	      stateTime = 0f;
	}
	
	public TextureRegion getCurrentCharacter() {
		return currentCharacter;
	}
	
	public void render(float delta) {
		stateTime += delta;
		currentCharacter = animation.getKeyFrame(stateTime, true);
		game.batch.draw(currentCharacter, px, py);
	}
	public void move(float x, float y) {
		this.px += x;
		this.py += y;
	}
	
	public int getX() {
		return px;
	}
	
	public int getY() {
		return py;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	public void setPosition(int x, int y) {
		this.px =x;
		this.py =y;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(px, py, width, height);
	}
	
}
