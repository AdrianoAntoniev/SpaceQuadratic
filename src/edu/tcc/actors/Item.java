package edu.tcc.actors;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Item extends BaseActor {
	private boolean collected;
	
	public Item(float x, float y, Stage s) {
		super(x, y, s);
		
		collected = false;
		loadTexture("actors/starfish.png");
		
		Action spin = Actions.rotateBy(30, 1);
		this.addAction(Actions.forever(spin));
		
		setBoundaryPolygon(8);
	}
	
	public boolean isCollected() {
		return collected;
	}
	
	public void collect() {
		collected = true;
		clearActions();
		addAction(Actions.fadeOut(1));
		addAction(Actions.after(Actions.removeActor()));

	}

}
