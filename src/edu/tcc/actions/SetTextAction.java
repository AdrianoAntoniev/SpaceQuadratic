package edu.tcc.actions;

import com.badlogic.gdx.scenes.scene2d.Action;

import edu.tcc.actors.DialogBox;

public class SetTextAction extends Action {
	protected String textToDisplay;
	
	public SetTextAction(String textToDisplay) {
		this.textToDisplay = textToDisplay;
	}
	
	@Override
	public boolean act(float dt) {
		DialogBox db = (DialogBox)target;
		db.setText(textToDisplay);
		return true;
		
	}

}
