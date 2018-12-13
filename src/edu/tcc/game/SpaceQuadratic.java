package edu.tcc.game;

import edu.tcc.screens.MenuScreen;

public class SpaceQuadratic extends BaseGame {

	@Override
	public void create() {
		super.create();
		setActiveScreen(new MenuScreen());
		
	}

}
