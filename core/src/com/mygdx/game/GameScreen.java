package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.characters.Hero;
import com.mygdx.game.characters.Projectiles;
import com.mygdx.game.characters.Villain;
import com.mygdx.game.characters.VillainManager;
import com.mygdx.game.managers.ResourceManager;

public class GameScreen implements  Screen{

	MyGdxGame game;
	OrthographicCamera camera;
	
	//Screen joystick variables
	Touchpad leftTouchPad;
	Touchpad rightTouchPad;
	Stage touchPadStage;
	
	//variables for creating the main character
	Hero hero;
	Array<Projectiles> missiles;
	VillainManager villains;
	Villain villain;
	//variable to manage shooting. Prevent stream of bullets;
	boolean touchShot;
	public GameScreen(MyGdxGame game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameConstants.WIDTH, GameConstants.HEIGHT);
		
		ResourceManager.getInstance().loadGameGraphics();
		hero = new Hero(game, ResourceManager.getInstance().hero, 8 , 1, 0.1f, 100, 100);
		//villain = new Villain(game, ResourceManager.getInstance().villain1, 7, 1, 0.1f, 100, 100);
		villains = new VillainManager(game, 0.1f);
		missiles = new Array<Projectiles>();
		touchShot = false;
		
		createTouchPads();
	}
	
	private void createTouchPads() {
		TouchpadStyle touchStyle = new TouchpadStyle();
		
		touchStyle.background = ResourceManager.getInstance().touchPadSkin.getDrawable("touchBackground");
		touchStyle.knob = ResourceManager.getInstance().touchPadSkin.getDrawable("knob");
		
		leftTouchPad = new Touchpad(GameConstants.TOUCH_DEAD_ZONE, touchStyle);
		rightTouchPad = new Touchpad(GameConstants.TOUCH_DEAD_ZONE, touchStyle);
       
		//setBounds(x,y,width,height)
        leftTouchPad.setBounds(GameConstants.TOUCH_X, GameConstants.TOUCH_Y, GameConstants.TOUCH_SIDE_LENGTH, GameConstants.TOUCH_SIDE_LENGTH);
        rightTouchPad.setBounds(GameConstants.WIDTH-(GameConstants.TOUCH_X+GameConstants.TOUCH_SIDE_LENGTH), GameConstants.TOUCH_Y, GameConstants.TOUCH_SIDE_LENGTH, GameConstants.TOUCH_SIDE_LENGTH);
        
 
        //Create a Stage and add TouchPad
        touchPadStage = new Stage();
        touchPadStage.addActor(leftTouchPad);
        touchPadStage.addActor(rightTouchPad);
        Gdx.input.setInputProcessor(touchPadStage);
	}

	private void handleInput(){
		if(leftTouchPad.isTouched()) {
			hero.move(leftTouchPad.getKnobPercentX(), leftTouchPad.getKnobPercentY());
		}
		
		if(rightTouchPad.isTouched()) {
			if(!touchShot) {
				double angle = Math.atan(rightTouchPad.getKnobPercentY()/ rightTouchPad.getKnobPercentX());
				float movey = (float) (Math.sin(angle)* GameConstants.MISSILE_SPEED);
				float movex = (float) (Math.cos(angle) * GameConstants.MISSILE_SPEED);
				
				missiles.add(hero.shoot(movex, movey));
				touchShot =true;
			}
		}else {
			touchShot = false;
		}
	}
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		 Gdx.gl.glClearColor(255, 255, 255, 1);
	     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
 
	     camera.update();

	     touchPadStage.act(Gdx.graphics.getDeltaTime());
	     touchPadStage.draw();
	     
	     game.batch.setProjectionMatrix(camera.combined);	        
	     game.batch.begin();   
	     hero.render(delta);
	     villains.checkCollisions(hero, missiles);
	     villains.generate();
	     villains.render(delta);
	     
	     Iterator<Projectiles> iter = missiles.iterator();
	     while(iter.hasNext()) {
	    	 Projectiles p = iter.next();
	    	 p.render(delta);
	    	 p.move();
	    	 if(p.getBeyond()) iter.remove();
	    	     	 
	     }
	     for(Projectiles p: missiles) {
	    	 p.render(delta);
	    	 p.move();
	     }
	     game.batch.end();
	     
	     handleInput();


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
