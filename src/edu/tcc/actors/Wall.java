package edu.tcc.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Wall extends BaseActor {

	public Wall(float x, float y, Stage s) {
		super(x, y, s);
		loadTexture("actors/gray wall.png");	
	}
	
	public void adjustWidthAndHeight(float width, float height) {
		setWidth(width + 10);
		setHeight(height + 10);
		
		setBoundaryPolygon(3);
	}
	
	@Override
	public void act(float dt) {
		super.act(dt);
		
		applyPhysics(dt);		
	}

	public void stretch() {
		setHeight(getHeight() + 5);		
	}
}