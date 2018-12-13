package edu.tcc.actors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import edu.tcc.utils.GameMeasures;

public class Rock extends BaseActor {
	private int speed;
	private int destructionValue;
	
	private static long numberOfInstances = 0;
	
	public Rock(float x, float y, Stage s) {
		super(x, y, s);		
		loadTexture("actors/rock.png");
					
		System.out.println(++this.numberOfInstances);
		
		this.resetPositionAndSize();
	}
	
	@Override
	public void act(float dt) {
		super.act(dt);
		
		if(this.reachedTheLeftSide()) {
			setX(GameMeasures.SPACE_WIDTH.get());
		} else {
			setX(getX() - this.speed);			
		}
	}
		

	public boolean reachedTheLeftSide() {
		return getX() <= -50;
	}
	
	public int getDestructionValue() {
		return this.destructionValue;
	}	
	
	public void resetPositionAndSize() {
		int randomSize = MathUtils.random(17, 80);		
		setWidth(randomSize);
		setHeight(randomSize);
		
		// de 17x17 ate 37x37, vale 20 pontos;
		// de 38x38 ate 58x58, vale 40 pontos;
		// de 59x59 ate 80x80, vale 60 pontos.
		this.destructionValue = randomSize < 38 ?
									20 : randomSize < 59 ?
											40 : 60;		
				
		setBoundaryPolygon(8);
		
		Action spin = Actions.rotateBy(MathUtils.random(30,50), 5);
		this.addAction(Actions.forever(spin));
		
		this.speed = MathUtils.random(2, 6);
	}
	
	@Override
	public boolean remove() {		
		return super.remove();
	}
}
