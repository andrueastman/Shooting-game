package com.mygdx.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.managers.ResourceManager;

public class Hero extends AnimatedCharacter{

	public Hero(MyGdxGame game, Texture tx, int rows, int cols,
			float frameDuration, int px, int py) {
		super(game, tx, rows, cols, frameDuration, px, py);
		// TODO Auto-generated constructor stub
	}
	
	public Projectiles shoot(float f, float g) {
		return new Projectiles(this.game, ResourceManager.getInstance().missile, 1, 1, 0.1f, this.px+this.currentCharacter.getRegionWidth(), this.py+(this.currentCharacter.getRegionHeight()/2), f, g);
	}
	public void move(float x, float y) {
		this.px += 10 * x;
		this.py += 10* y;
		if(this.px<0)this.px =0;
		if(this.px>800-currentCharacter.getRegionWidth()) this.px =800-currentCharacter.getRegionWidth();
		if(this.py > 480-currentCharacter.getRegionHeight() )this.py=480-currentCharacter.getRegionHeight();
		if(this.py<0)this.py=0;
	}
	
	public void reset() {
		this.px =100;
		this.py = 100;
	}



}
