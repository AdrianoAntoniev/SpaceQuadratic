package edu.tcc.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class HPBar extends BaseActor {	
	private ExternalHpBar externalHpBar;
	
	public HPBar(float x, float y, Stage s) {
		super(x, y, s);
		
		loadTexture("ui/hp.png");				
		this.externalHpBar = new ExternalHpBar(x, y, s);
	}
	
	@Override
	public void setWidth(float width) {
		super.setWidth(width);
		this.externalHpBar.setWidth(width);
	}
	
	@Override
	public void setHeight(float height) {
		super.setHeight(height);
		this.externalHpBar.setHeight(height);
	}
	
	@Override
	public void setX(float x) {
		super.setX(x);
		this.externalHpBar.setX(x);
	}
	
	@Override
	public void setY(float y) {
		super.setY(y);
		this.externalHpBar.setY(y);
	}

	
	private class ExternalHpBar extends BaseActor {
		public ExternalHpBar(float x, float y, Stage s) {
			super(x, y, s);
			
			loadTexture("ui/bg-hp.png");			
		}	
	}

	public void decrement(int value) {
		this.externalHpBar.setWidth(this.externalHpBar.getWidth() - value);
	}
	
	@Override
	public boolean remove() {
		return super.remove() && this.externalHpBar.remove();
	}
	
	public float getCurrentHPValue() {
		return this.externalHpBar.getWidth();
	}

	public void setCurrentHPValue(float value) {
		this.externalHpBar.setWidth(value);		
	}
}
