package edu.tcc.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import edu.tcc.utils.GameMeasures;

public class ParallaxBackgroundActor extends Actor {
	private Texture texture;
	private int scroll = 0;
	float x;
	private float y;
	private int speed;
	
	public ParallaxBackgroundActor(float x, float y, Stage s) {			
		this.texture = new Texture(Gdx.files.internal("actors/space.png"));
		this.texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		super.setSize(GameMeasures.SPACE_WIDTH.get(), GameMeasures.SPACE_HEIGHT.get());
		
		this.x = x;
		this.y = y;
		s.addActor(this);
	}	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(getColor().r, getColor().g, 
				getColor().b, getColor().a * parentAlpha);
		
		this.scroll += this.speed;
		batch.draw(this.texture, 0, 0, this.scroll, 0, 
				(int)GameMeasures.SPACE_WIDTH.get(), (int)GameMeasures.SPACE_HEIGHT.get());
	}

	public void setSpeed(int speed) {
		this.speed = speed;		
	}	
	
	/**
	 * A ser implementado futuramente
	 */
	public void changeBackgroundSpeed() {}
}
