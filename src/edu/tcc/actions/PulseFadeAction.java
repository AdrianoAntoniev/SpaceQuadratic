package edu.tcc.actions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class PulseFadeAction extends SequenceAction {
	public PulseFadeAction() {
		super();				
		
		addAction(Actions.sequence(Actions.scaleTo(1.1f, 1.1f, 0.05f),
				Actions.scaleTo(1.0f, 1.0f, 0.05f), 
				Actions.color(Color.WHITE, 1), Actions.delay(1)));
		
		
	}
	
	
}
