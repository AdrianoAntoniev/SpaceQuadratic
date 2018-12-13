package edu.tcc.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class BaseActorLabel extends BaseActor {
	private Label label;
	private Stage stage;
	
	public BaseActorLabel(float x, float y, Stage stage, Label label) {
		super(x, y, stage);
		
		this.stage = stage;
		this.label = label;		
		this.stage.addActor(this.label);

	}
	
	@Override
	public void act(float dt) {
		super.act(dt);
		
	}	
}
