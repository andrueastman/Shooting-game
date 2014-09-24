package com.mygdx.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.MyGdxGame;

public class Villain extends AnimatedCharacter{

	public Villain(MyGdxGame game, Texture tx, int rows, int cols,
			float frameDuration, int px, int py) {
		super(game, tx, rows, cols, frameDuration, px, py);
		// TODO Auto-generated constructor stub
	}
	
	public void move() {
		this.px -= 3;
		this.py += 0;
	//	if(this.px<0)this.px =0;
		//if(this.px>800-currentCharacter.getRegionWidth()) this.px =800-currentCharacter.getRegionWidth();
		//if(this.py > 480-currentCharacter.getRegionHeight() )this.py=480-currentCharacter.getRegionHeight();
		//if(this.py<0)this.py=0;
	}

}
