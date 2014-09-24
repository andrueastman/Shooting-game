package com.mygdx.game.characters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.GameConstants;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.managers.ResourceManager;
import com.mygdx.game.characters.Villain;

public class VillainManager {
	
	MyGdxGame game;
	float frameDuration;
	ArrayList<Villain> villains;
	public static VillainPool VILLAIN_POOL;
	
	public VillainManager(MyGdxGame game,float frameDuration) {
		this.game = game;
		this.frameDuration = frameDuration;
		villains = new ArrayList<Villain>();
		VILLAIN_POOL = new VillainPool();
	}
	
	public ArrayList<Villain> getVillains(){
		return villains;
	}
	
	public Villain[] generate(){
		Villain [] array = null;
		if(villains.isEmpty()){
			Villain v= VILLAIN_POOL.obtain();
			villains.add(v);	
		}else{
			if(GameConstants.WIDTH -villains.get(villains.size()-1).getX() >170){
					Villain v = VILLAIN_POOL.obtain();
					v.setPosition(GameConstants.WIDTH ,MathUtils.random(0, GameConstants.HEIGHT));
					villains.add(v);
			}
		}
		return array;
	}
	
	public void render(float delta) {
		Iterator<Villain> it = villains.iterator();
		while(it.hasNext()) {
			Villain v = it.next();
			if(checkOffScreen(it, v)) continue;
			v.move();
			v.render(delta);
		}
	}
	public boolean checkOffScreen(Iterator v, Villain c) {
		if(c.getX()+c.getWidth()< 0){
				
				v.remove();
				VILLAIN_POOL.free(c);
				return true;
			}			
		return false;
	}
	
	public void checkCollisions(Hero h, Array<Projectiles> a) {
		Iterator<Villain> it = villains.iterator();
		while(it.hasNext()) {
			Villain v = it.next();
			if(v.getBounds().overlaps(h.getBounds())) {
				//do sth about points
				h.reset();
				it.remove();
				continue;
			}
			if(checkProjectileCollision(a, v)) {
				it.remove();
				continue;
			}
		}
	}
	
	public boolean checkProjectileCollision(Array<Projectiles> a, Villain v) {
		for(Projectiles b: a) {
			if(v.getBounds().overlaps(b.getBounds())) {
				b.beyond = true;
				return true;
			}
		}
		return false;
		
	}
/*	public boolean checkCollisions(Sprite s,Scene sc){
		for(Villain v: villains){
			if (s.collidesWith(v)){
				sc.detachChild(v);
				if(villains.remove(v))
					VILLAIN_POOL.recyclePoolItem(v);
				return true;
			}
			//return false;
		}
		return false;
	}*/


	public class VillainPool extends Pool<Villain>{

		@Override
		protected Villain newObject() {
			// TODO Auto-generated method stub
			return new Villain(game, ResourceManager.getInstance().villain1, 7, 1, 0.1f, 0, 0);
		}
		
		public Villain obtain() {
			return super.obtain();
		}
		
	}
/*	public class VillainPool extends GenericPool<Villain>{
		private ITiledTextureRegion mTextureRegion;
		
		public VillainPool(ITiledTextureRegion mtexture) {
			if(mtexture ==null)
				throw new IllegalArgumentException("Texture must not be null");
			mTextureRegion= mtexture;
		}

		protected void onHandleRecycleItem(Sprite pItem) {
			//pItem.detachSelf();
			pItem.clearEntityModifiers();
			pItem.clearUpdateHandlers();
			pItem.setIgnoreUpdate(true);
			pItem.setVisible(false);
			
			pItem.reset();
		}

		@Override
		protected Villain onAllocatePoolItem() {
			return new Villain(mTextureRegion,vbom, camera);
		}	
		
	}
*/
}
