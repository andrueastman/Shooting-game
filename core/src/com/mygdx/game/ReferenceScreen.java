package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class ReferenceScreen implements Screen{

	MyGdxGame game;
	
	Texture dropImage, bucketImage;
	TextureRegion[] hero;
	TextureRegion currentHero;
	Sound dropSound;
	Music rainMusic;
	OrthographicCamera camera;
	
	Rectangle bucket;
	Rectangle backgroundRect;
	
	Array<Rectangle> raindrops;
	long lastDropTime;

	int dropsGathered;
	
	
	///variables to create onscreen knob
	Touchpad touch;
	TouchpadStyle touchStyle;
	Skin touchSkin;
	Stage stage;
	
	Skin background;
	ParallaxBackground rbg;
	private Animation anim;
	
	float statetime;
	public ReferenceScreen(MyGdxGame game) {
		this.game = game;
	
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
		// load the drop sound effect and the rain background "music"
	      dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.mp3"));
	      rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
	      Texture sheet = new Texture(Gdx.files.internal("planeherotiled.png"));
	      TextureRegion [][] tmp = TextureRegion.split(sheet,sheet.getWidth()/ 7, sheet.getHeight()/1);
	      
	      hero = new TextureRegion[7 * 1];
	        int index = 0;
	        for (int i = 0; i < 1; i++) {
	            for (int j = 0; j < 7; j++) {
	                hero[index++] = tmp[i][j];
	            }
	        }
	      // start the playback of the background music immediately
	      rainMusic.setLooping(true);
	      rainMusic.play();
	      
	      anim = new Animation(1.0f, hero);
	      //creating camera
	      camera = new OrthographicCamera();
	      camera.setToOrtho(false,800,480);
	      
	      bucket = new Rectangle();
	      bucket.x = 800 / 2 - 64 / 2;
	      bucket.y = 20;
	      bucket.width = 64;
	      bucket.height = 64;
	      statetime = 0f;
	      raindrops = new Array<Rectangle>();
	      createTouchPad();
	      spawnRaindrop();
	      createAnimatedBackground();
	      /*rbg = new ParallaxBackground(new ParallaxLayer[]{
	              new ParallaxLayer(background.getRegion("back"),new Vector2(480,0),new Vector2(0, 0)),
	              new ParallaxLayer(background.getRegion("back"),new Vector2(1.0f,1.0f),new Vector2(0, 500)),
	              new ParallaxLayer(background.getRegion("back"),new Vector2(0.1f,0),new Vector2(0,800-200),new Vector2(0, 0)),
	        }, camera,new Vector2(150,0));*/

	}
	
	private void spawnRaindrop() {
	      Rectangle raindrop = new Rectangle();
	      raindrop.x = MathUtils.random(0, 800-64);
	      raindrop.y = 480;
	      raindrop.width = 64;
	      raindrop.height = 64;
	      raindrops.add(raindrop);
	      lastDropTime = TimeUtils.nanoTime();
	   }
	
	private void createTouchPad() {
		touchSkin = new Skin();
		touchSkin.add("touchBackground", new Texture(Gdx.files.internal("hudcircle.png")));
		touchSkin.add("knob", new Texture(Gdx.files.internal("onscreen_control_knob.png")));
		touchStyle = new TouchpadStyle();
		
		touchStyle.background = touchSkin.getDrawable("touchBackground");
		touchStyle.knob = touchSkin.getDrawable("knob");
		
		//Create new TouchPad with the created style
        touch = new Touchpad(10, touchStyle);
        //setBounds(x,y,width,height)
        touch.setBounds(15, 15, 200, 200);
 
        //Create a Stage and add TouchPad
        //stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, batch);
        stage = new Stage();
        stage.addActor(touch);            
        Gdx.input.setInputProcessor(stage);
	}

	private void createAnimatedBackground() {
		background = new Skin();
		background.add("back", new Texture(Gdx.files.internal("parallax_background_layer_back.png")));
		background.add("middle", new Texture(Gdx.files.internal("parallax_background_layer_mid.png")));
		background.add("front", new Texture(Gdx.files.internal("parallax_background_layer_front.png")));
	}
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		 Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	        // tell the camera to update its matrices.
	        camera.update();

	        // tell the SpriteBatch to render in the
	        // coordinate system specified by the camera.
	        game.batch.setProjectionMatrix(camera.combined);
	        statetime += delta;
	        currentHero = anim.getKeyFrame(statetime, true);
	        // begin a new batch and draw the bucket and
	        // all drops
	        game.batch.begin();
	        game.batch.draw(background.getRegion("back"), 0, 0);
	        game.batch.draw(currentHero, 100, 100);
	        game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 480);
	        game.batch.draw(bucketImage, bucket.x, bucket.y);
	        for (Rectangle raindrop : raindrops) {
	            game.batch.draw(dropImage, raindrop.x, raindrop.y);
	        }
	        game.batch.end();

	        
	        if(touch.isTouched()) {
	        	bucket.x += 10 * touch.getKnobPercentX();
	        }
	        // process user input
	        /*
	        if (Gdx.input.isTouched()) {
	            Vector3 touchPos = new Vector3();
	            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
	            camera.unproject(touchPos);
	            bucket.x = touchPos.x - 64 / 2;
	        }*/
	        if (Gdx.input.isKeyPressed(Keys.LEFT))
	            bucket.x -= 200 * Gdx.graphics.getDeltaTime();
	        if (Gdx.input.isKeyPressed(Keys.RIGHT))
	            bucket.x += 200 * Gdx.graphics.getDeltaTime();

	        // make sure the bucket stays within the screen bounds
	        if (bucket.x < 0)
	            bucket.x = 0;
	        if (bucket.x > 800 - 64)
	            bucket.x = 800 - 64;

	        // check if we need to create a new raindrop
	        if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
	            spawnRaindrop();

	        // move the raindrops, remove any that are beneath the bottom edge of
	        // the screen or that hit the bucket. In the later case we increase the 
	        // value our drops counter and add a sound effect.
	        Iterator<Rectangle> iter = raindrops.iterator();
	        while (iter.hasNext()) {
	            Rectangle raindrop = iter.next();
	            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
	            if (raindrop.y + 64 < 0)
	                iter.remove();
	            if (raindrop.overlaps(bucket)) {
	                dropsGathered++;
	                dropSound.play();
	                iter.remove();
	            }
	        }		
	        
	        stage.act(Gdx.graphics.getDeltaTime());        
	        stage.draw();
	 
	    
	}
	
	public class ParallaxLayer{
		   public TextureRegion region ;
		   public Vector2 parallaxRatio;
		   public Vector2 startPosition;
		   public Vector2 padding ;
		   public ParallaxLayer(TextureRegion region,Vector2 parallaxRatio,Vector2 padding){
		      this(region, parallaxRatio, new Vector2(0,0),padding);
		   }
		   /**
		    * @param region   the TextureRegion to draw , this can be any width/height
		    * @param parallaxRatio   the relative speed of x,y {@link ParallaxBackground#ParallaxBackground(ParallaxLayer[], float, float, Vector2)}
		    * @param startPosition the init position of x,y
		    * @param padding  the padding of the region at x,y
		    */
		   public ParallaxLayer(TextureRegion region,Vector2 parallaxRatio,Vector2 startPosition,Vector2 padding){
		      this.region  = region;
		      this.parallaxRatio = parallaxRatio;
		      this.startPosition = startPosition;
		      this.padding = padding;
		   }
		}
	
	public class ParallaxBackground {
		   
		   private ParallaxLayer[] layers;
		   private OrthographicCamera camera;
		   private SpriteBatch batch;
		   private Vector2 speed = new Vector2();
		   
		   /**
		    * @param layers  The  background layers 
		    * @param width   The screenWith 
		    * @param height The screenHeight
		    * @param speed A Vector2 attribute to point out the x and y speed
		    */
		   public ParallaxBackground(ParallaxLayer[] layers,OrthographicCamera camera,Vector2 speed){
		      this.layers = layers;
		      this.speed.set(speed);
		      this.camera = camera;
		      batch = new SpriteBatch();
		   }
		   
		   public void render(float delta){
		      this.camera.position.add(speed.x*delta,speed.y*delta, 0);
		      for(ParallaxLayer layer:layers){
		         batch.setProjectionMatrix(camera.projection);
		         batch.begin();
		         float currentX = - camera.position.x*layer.parallaxRatio.x % ( layer.region.getRegionWidth() + layer.padding.x) ;
		         
		         if( speed.x < 0 )currentX += -( layer.region.getRegionWidth() + layer.padding.x);
		         do{
		            float currentY = - camera.position.y*layer.parallaxRatio.y % ( layer.region.getRegionHeight() + layer.padding.y) ;
		            if( speed.y < 0 )currentY += - (layer.region.getRegionHeight()+layer.padding.y);
		            do{
		               batch.draw(layer.region,
		                     -this.camera.viewportWidth/2+currentX + layer.startPosition.x ,
		                     -this.camera.viewportHeight/2 + currentY +layer.startPosition.y);
		               currentY += ( layer.region.getRegionHeight() + layer.padding.y );
		            }while( currentY < camera.viewportHeight);
		            currentX += ( layer.region.getRegionWidth()+ layer.padding.x);
		         }while( currentX < camera.viewportWidth);
		         batch.end();
		      }
		   }
		}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		rainMusic.play();
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
		 dropImage.dispose();
	        bucketImage.dispose();
	        dropSound.dispose();
	        rainMusic.dispose();
	}

}
