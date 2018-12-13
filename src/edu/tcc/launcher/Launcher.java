package edu.tcc.launcher;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import edu.tcc.game.SpaceQuadratic;
import edu.tcc.utils.GameMeasures;

public class Launcher {
	public static void main(String[] args) {
		System.out.println(com.badlogic.gdx.Version.VERSION);				
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "SPACE QUADRATIC";
		cfg.width = (int)GameMeasures.WINDOW_WIDTH.get();
		cfg.height = (int)GameMeasures.WINDOW_HEIGHT.get();
		
		new LwjglApplication(new SpaceQuadratic(), cfg);		
	}
}
