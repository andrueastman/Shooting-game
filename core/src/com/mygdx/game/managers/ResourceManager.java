package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ResourceManager {
//class that manages all resource of the games
	public static final ResourceManager INSTANCE = new ResourceManager();
	
	
	public Texture hero;
	public Skin touchPadSkin;
	public Texture missile;
	public Texture villain1;
	
	public void loadGameGraphics() {
		hero = new Texture(Gdx.files.internal("planes.png"));
		villain1 = new Texture(Gdx.files.internal("villain1.png"));
		touchPadSkin = new Skin();
		touchPadSkin.add("touchBackground", new Texture(Gdx.files.internal("hudcircle.png")));
		touchPadSkin.add("knob", new Texture(Gdx.files.internal("onscreen_control_knob.png")));
		missile = new Texture(Gdx.files.internal("missile.png"));
	}
	
	public static ResourceManager getInstance() {
		return INSTANCE;
	}
}
