package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.characters.AnimatedCharacter;
import com.mygdx.game.characters.Hero;
import com.mygdx.game.characters.Projectiles;
import com.mygdx.game.managers.ResourceManager;

public class TesterScreen implements Screen{
	
	MyGdxGame game;
	OrthographicCamera camera;
	Hero hero;
	Touchpad touch;
	Stage touchStage;
	
	Array<Projectiles> missiles;
	
	public TesterScreen(MyGdxGame game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		ResourceManager.INSTANCE.loadGameGraphics();
		missiles  = new Array<Projectiles>();
		hero = new Hero(game, ResourceManager.getInstance().hero,8 , 1, 0.1f, 100, 100);
		createTouchPad();
	}
	@Override
	public void render(float delta) {
		 Gdx.gl.glClearColor(255, 255, 255, 1);
	     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
 
	     camera.update();

	     touchStage.act(Gdx.graphics.getDeltaTime());
	     touchStage.draw();
	     
	     game.batch.setProjectionMatrix(camera.combined);	        
	     game.batch.begin();   
	     hero.render(delta);
	     for(Projectiles p: missiles) {
	    	 p.render(delta);
	    	// game.batch.draw(p.getCurrentCharacter(), p.px, p.py);
	     }
	     game.batch.end();

	     Iterator<Projectiles> iter = missiles.iterator();
	     while(iter.hasNext()) {
	    	 Projectiles p = iter.next();
	    	 p.move();
	    	 if(p.getBeyond()) iter.remove();
	     }
	     handleInput();
	}
	private void createTouchPad() {
		TouchpadStyle touchStyle = new TouchpadStyle();
		
		touchStyle.background = ResourceManager.getInstance().touchPadSkin.getDrawable("touchBackground");
		touchStyle.knob = ResourceManager.getInstance().touchPadSkin.getDrawable("knob");
		
		//Create new TouchPad with the created style
        touch = new Touchpad(10, touchStyle);
        //setBounds(x,y,width,height)
        touch.setBounds(15, 15, 200, 200);
 
        //Create a Stage and add TouchPad
        //stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, batch);
        touchStage = new Stage();
        touchStage.addActor(touch);            
        Gdx.input.setInputProcessor(touchStage);
	}
	
	boolean shot = false;
	private void handleInput() {
		if(touch.isTouched()) {
			if(!shot) {
				//missiles.add(hero.shoot());
				shot = true;
				};
			hero.move(touch.getKnobPercentX(), touch.getKnobPercentY());
			
		}else {
			shot = false;
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
