package com.mygdx.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.MyGdxGame;

public class Projectiles extends AnimatedCharacter{

	float movex, movey;
	boolean beyond;
	
	public Projectiles(MyGdxGame game, Texture tx, int rows, int cols,
			float frameDuration, int px, int py, float movex, float movey) {
		super(game, tx, rows, cols, frameDuration, px, py);
		beyond = false;
		System.out.println(movex);
		
		this.movex = 5* movex;
		this.movey =  5* movey;
	}
	
	public boolean getBeyond() {
		return beyond;
	}
	
	public void render(float delta) {
		stateTime += delta;
		currentCharacter = animation.getKeyFrame(stateTime, true);
		
		game.batch.draw(currentCharacter, px, py, 0,0, currentCharacter.getRegionWidth(), currentCharacter.getRegionHeight(), 1, 1, (int)Math.toDegrees(Math.atan(movey/movex)));
		//game.batch.draw(currentCharacter, px, py);
	}
	public void move() {
		this.move(movex, movey);
		if(this.px<=0 || this.py<=0 || this.px>= 800- currentCharacter.getRegionWidth() || this.py>= 480- currentCharacter.getRegionHeight()) {
			beyond = true;
		}
	}

}
