package edu.tcc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import edu.tcc.actors.ParallaxBackgroundActor;
import edu.tcc.game.BaseGame;
import edu.tcc.game.SpaceQuadratic;

public class MenuScreen extends BaseScreen {
	@Override
	public void initialize() {
		ParallaxBackgroundActor space = new ParallaxBackgroundActor(0, 0, mainStage);		
		space.setSpeed(1);				
		
		Label title = new Label("Space Quadratic", BaseGame.titleLabelStyle);
		title.setColor(Color.GREEN);
		title.setSize(400, 400);
		
		TextButton startButton = new TextButton("Iniciar", BaseGame.textButtonStyle);
		startButton.addListener((Event e) -> {
			if ((e instanceof InputEvent)) {
				InputEvent ie = (InputEvent)e;
				if (ie.getType().equals(Type.touchDown)) {
					SpaceQuadratic.setActiveScreen(new LevelScreen());
					return true;
				}
			}
			return false;
		});
		
		TextButton quitButton = new TextButton("Sair", BaseGame.textButtonStyle);
		
		quitButton.addListener((Event e) -> {
			if ((e instanceof InputEvent)) {
				InputEvent ie = (InputEvent)e;
				if (ie.getType().equals(Type.touchDown)) {
					Gdx.app.exit();					
				}
			}
			return false;
		});
		
		uiTable.add(title).colspan(2);
		uiTable.row();
		uiTable.add(startButton);
		uiTable.add(quitButton);
		
	}

	@Override
	public void update(float dt) {
		
	}
	
	@Override
	public boolean keyDown(int keyCode) {
		if(Gdx.input.isKeyPressed(Keys.ENTER))
			SpaceQuadratic.setActiveScreen(new LevelScreen());
		if(Gdx.input.isKeyPressed(Keys.ESCAPE))
			Gdx.app.exit();
		return false;
	}

}
