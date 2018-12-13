package edu.tcc.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

import edu.tcc.utils.GameMeasures;

public class ParabolicLaser extends Laser {
	private float a;
	private float b;
	private float c;
	private float x1;
	private float zeroXPoint;
	private float zeroYPoint;
	private float x2;
	
	public ParabolicLaser(Stage s, float a, float b, float c) {
		super(s);
		
		this.a = a;
		this.b = b;
		this.c = c;									
		
		this.zeroXPoint = ((GameMeasures.X_BOSS_POSITION.get() + getX()) / 2); 
		this.zeroYPoint = GameMeasures.Y_BOSS_POSITION.get();				
		this.x1 = getX();		
		this.x2 = GameMeasures.X_BOSS_POSITION.get();
		
		setY(GameMeasures.Y_LASER_ORIGIN.get());	
	}
		
	@Override
	public void act(float dt) {
		super.act(dt);				
	}
	
	/**
	 *  Aqui tenta-se encontrar y com base na equacao do segundo grau:
	 *	ax² + bx + c; x eh a atual posicao da nave
	 */
	@Override
	protected void verifyShipPositionAndLaunchLaser() {			
		double coefficientA = 0.0;
		if(this.a != 0.0) 					
			coefficientA = this.a * Math.pow((this.x1 / this.zeroXPoint), 2);
		double coefficientB = this.b * ((this.x1) / this.zeroXPoint);
		double coefficientC = this.c;			
		
		float valueOfY = (float) (coefficientA + coefficientB + coefficientC);		
		
		setY(getY() + valueOfY);
		
		setX((this.x1 += 1f));	
		
	}
}
