package edu.tcc.utils;

public enum GameMeasures {
	SPACE_WIDTH(1800), 
	SPACE_HEIGHT(1200),
	WINDOW_WIDTH(1200), 
	WINDOW_HEIGHT(700),
	DIALOG_BOX_WIDTH(700),
	DIALOG_BOX_HEIGHT(400),
	X_BOSS_POSITION(0),
	Y_BOSS_POSITION(0),
	X_LASER_ORIGIN(0),
	Y_LASER_ORIGIN(0);
	
	private float value;
	
	private GameMeasures(float value) {
		this.value = value;
	}
	
	public float get() {
		return this.value;
	}
	
	/**
	 * Code smell! Atualizando o valor de um enum ¬¬
	 * 
	 * @param value O novo valor.
	 */
	public void set(float value) {
		this.value = value;
	}		
}
