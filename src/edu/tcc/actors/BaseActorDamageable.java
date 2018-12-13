package edu.tcc.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class BaseActorDamageable extends BaseActor implements Damageable {
	private int damageValue = 5;
	private int damageValueInHPBar;
	private HPBar hpBar;
	private float hp;	
	private Stage stage;
	
	public BaseActorDamageable(float x, float y, Stage s) {
		super(x, y, s);		
		this.stage = s;		
		
		this.hp = 100;
	}

	@Override
	public void deductDamage() {
		if(this.damageValueInHPBar == 0) {
			this.damageValueInHPBar = (int) ((this.damageValue * getWidth()) / this.hp); 
		}
		
		if(isAlive()) {			
			this.hp -= damageValue;
			this.hpBar.decrement(damageValueInHPBar);			
		}	
	}

	public boolean isAlive() {		
		return this.hp > 0;
	}
	
	
	public void configureHpBar() {
		this.hpBar = new HPBar(this.getX(), 
				this.getY() + 20 + this.getHeight(), this.stage);
		this.hpBar.setWidth(this.getWidth());
		this.hpBar.setHeight(10);		
	}
	
	public void updateHpBarPosition() {
		this.hpBar.setX(this.getX());
		this.hpBar.setY(this.getY());
	}
	
	public boolean removeHPBar() {		
		return this.hpBar.remove();
	}
	
	public float getCurrentHPValue() {
		return this.hpBar.getCurrentHPValue();
	}
	
	public void setCurrentHPValue(float value) {
		this.hpBar.setCurrentHPValue(value);
	}
	
	public HPBar getHpBar() {
		return hpBar;
	}
}
