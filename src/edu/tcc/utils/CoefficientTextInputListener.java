package edu.tcc.utils;

import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class CoefficientTextInputListener implements TextInputListener {	
	private Label label;

	public CoefficientTextInputListener(Label label) {
		this.label = label;
	}
	
	@Override
	public void input(String text) {
		if(text.matches("^(-)?[0-9]+$") || text.matches("")) {
			label.setText(text);
		}
	}

	@Override
	public void canceled() {		
	}
}


