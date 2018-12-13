package edu.tcc.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class BossShip extends BaseActorDamageable {	
	public BossShip(float x, float y, Stage s) {
		super(x, y, s);
		
		setAcceleration(400);
		setMaxSpeed(100);
		setDeceleration(400);		
		rotateBy(90);
		
		String[] fileName = {"actors/boss.png"};		
		loadAnimationFromFiles(fileName, 0.1f, true);		
		setBoundaryPolygon(8);		
	}
	
	@Override
	public void act(float dt) {
		super.act(dt);
		
		applyPhysics(dt);
		setAnimationPaused(!isMoving());
		
		if(getSpeed() > 0)
			setRotation(getMotionAngle());		
	}	
}
