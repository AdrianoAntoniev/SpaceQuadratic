package edu.tcc.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.tcc.utils.GameMeasures;

public class Ship extends BaseActorDamageable {
    /**
     * Variavel usada para identificar a direcao da nave.
     * Util somente para o caso de a nave se mover como 
     * Asteroids... Se a nave se mover como SpaceInvaders, 
     * nao eh necessario, ja que a nave ficara apenas virada
     * pra direita.
     */
	private int currentDirection;
	private float initialX, initialY;
	private boolean needToUseParabolicLaser;
	private boolean lockedUpAndDownMovement;
	private float a;
	private float b;
	private float c;
	private float xBossPosition;

	public Ship(float x, float y, Stage s) {
		super(x, y, s);
		
		initialX = x;
		initialY = y;
		setAcceleration(400);
		setMaxSpeed(100);
		setDeceleration(400);
		
		String[] fileName = {"actors/spaceship.png"};			
		loadAnimationFromFiles(fileName, 0.1f, true);		
		setBoundaryPolygon(8);		
		
		this.lockedUpAndDownMovement = false;
	}

	public float getInitialX() {
		return this.initialX;
	}
	
	public float getInitialY() {
		return this.initialY;
	}
	
	public void setInitialX(float x) {
		this.initialX = x;
	}

	public void setInitialY(float y) {
		this.initialY = y;		
	}	

	@Override
	public void act(float dt) {
		super.act(dt);
		this.verifyKeys();
		
		applyPhysics(dt);
		setAnimationPaused(!isMoving());
		
		if(getSpeed() > 0)
			setRotation(getMotionAngle());
		
		boundToWorld();
		alignCamera();
		
		this.updateHpBarPosition();
	}	
	
	private void verifyKeys() {		
		if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			Stage stage = getStage();
			GameMeasures.X_LASER_ORIGIN.set(this.getX() + this.getWidth());
			GameMeasures.Y_LASER_ORIGIN.set((this.getHeight() / 2) + this.getY() - 7);					
						
			if(this.needToUseParabolicLaser) {
				stage.addActor(new ParabolicLaser(stage, a, b, c));		
			} else {
				stage.addActor(new Laser(stage));
			}
		}
		
		this.verifyKeysAndMoveLikeACross();
	}
	
	/**
	 * Dependendo da tecla pressionada, o ator se move em cruz 
	 * (Como SpaceInvaders).
	 * Mais formalmente, o ator se move porem mantem-se alinhado 
	 * para o lado direito da tela.  
	 */
	private void verifyKeysAndMoveLikeACross() {
		if(Gdx.input.isKeyPressed(Keys.DOWN)) {
			if(!this.lockedUpAndDownMovement)
				moveBy(0, -2);
		}				
		if(Gdx.input.isKeyPressed(Keys.UP)) {
			if(!this.lockedUpAndDownMovement)
				moveBy(0, 2);
		}			
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			moveBy(-2, 0);
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			moveBy(2, 0);
		}
	}	
	/**
	 * Dependendo da tecla pressionada, o ator se move como carrinho de bate bate.
	 * (Semelhante a Asteroids).
	 * Mais formalmente, o ator fica alinhado para a direcao pressionada.
	 * 
	 * 
	 */
	private void verifyKeysAndMoveLikeAScroller() {
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			this.currentDirection = Keys.LEFT;
			accelerateAtAngle(180);
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			this.currentDirection = Keys.RIGHT;
			accelerateAtAngle(0);
		}
		if(Gdx.input.isKeyPressed(Keys.UP)) {
			this.currentDirection = Keys.UP;
			accelerateAtAngle(90);
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN)) {
			this.currentDirection = Keys.DOWN;
			accelerateAtAngle(270);
		}
	}
	
	@Override
	public void configureHpBar() {
		super.configureHpBar();
	}
	
	@Override
	public void updateHpBarPosition() {
		this.getHpBar().setX(this.getX());
		this.getHpBar().setY(this.getY() - 10);
	}
	
	public void setParabolicLaser(float a, float b, float c, float zeroInAxisY) {
		this.needToUseParabolicLaser = true;
		
		GameMeasures.Y_BOSS_POSITION.set((int) zeroInAxisY);
		this.a = a;
		this.b = b;
		this.c = c;
	}	
	
	public void setHorizontalLaser() {
		this.needToUseParabolicLaser = false;
	}
	
	public void lockUpAndDownMovement() {
		this.lockedUpAndDownMovement = true;
	}

	public void pushBack() {
		setX(getX() - getWidth() * 4);
		
	}	
}
