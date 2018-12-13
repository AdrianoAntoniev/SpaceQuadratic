package edu.tcc.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

import edu.tcc.utils.GameMeasures;

public class Laser extends BaseActor {
	protected static final int SPEED = 4;
	private float currentRotation;
	
	public Laser(Stage s, float currentRotation) {
		this(s);
		this.currentRotation = currentRotation;		
	}
	
	public Laser(Stage s) {
		super(GameMeasures.X_LASER_ORIGIN.get(), GameMeasures.Y_LASER_ORIGIN.get(), s);
		loadTexture("actors/laser.png");
		setWidth(15);
		setHeight(15);						
		
		setBoundaryPolygon(8);
	}	
	
	@Override
	public void act(float dt) {
		super.act(dt);
		
		this.verifyShipPositionAndLaunchLaser();		
	}

	/*
	 * Versao tosca para este metodo! Aqui este metodo so entra no segundo IF, 
	 * ja que a nave (por enquanto) nao muda a direcao. Por isso, a rotacao atual
	 * sera sempre 0. 
	 */
	protected void verifyShipPositionAndLaunchLaser() {
		int intCurrentRotation = Math.round(this.currentRotation); 				
		
		if(intCurrentRotation == 90) {
			setY(getY() + this.SPEED);
		} else if(intCurrentRotation == 0) {
			setX(getX() + this.SPEED);							
		} else if(intCurrentRotation == 180) {
			setX(getX() - this.SPEED) ;
		} else if(intCurrentRotation == 270) {
			setY(getY() - this.SPEED);
		} 
	}
	
	public boolean isInsideOfScreen() {
		return this.getX() > 0 && 
			   this.getX() < GameMeasures.SPACE_WIDTH.get() &&
			   this.getY() > 0 &&
			   this.getY() < GameMeasures.SPACE_HEIGHT.get();
	}
}
