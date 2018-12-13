package edu.tcc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import edu.tcc.actors.BaseActor;
import edu.tcc.actors.DialogBox;
import edu.tcc.actors.Laser;
import edu.tcc.actors.ParallaxBackgroundActor;
import edu.tcc.actors.Rock;
import edu.tcc.actors.Ship;
import edu.tcc.game.BaseGame;
import edu.tcc.game.SpaceQuadratic;
import edu.tcc.utils.GameMeasures;

public class LevelScreen extends BaseScreen {
	private Ship ship;
	private ParallaxBackgroundActor space;
	private boolean win;
	private Label pointsLabel;
	private DialogBox dialogBox;
	private float audioVolume;
	private Sound waterDrop;
	private Music instrumental;
	private Music oceanSurf;	
	private long pointsLimit;
	
	private long points = 0L;
	private boolean gameOver;
	private Label youLoseLabel;
	
	@Override
	public void initialize() {									
		space = new ParallaxBackgroundActor(0, 0, mainStage);
		space.setSpeed(3);
		BaseActor.setWorldBounds(space);

		for (int i = 0; i < 5; i++)
			createNewRock();

		this.ship = new Ship(20, 20, mainStage);
		this.ship.configureHpBar();		
		pointsLabel = new Label("Pontos: ", BaseGame.labelStyle);
		pointsLabel.setColor(Color.GRAY);

		ButtonStyle buttonStyle = new ButtonStyle();
		Texture buttonTex = new Texture(Gdx.files.internal("keys/undo.png"));
		TextureRegion buttonRegion = new TextureRegion(buttonTex);
		buttonStyle.up = new TextureRegionDrawable(buttonRegion);

		Button restartButton = new Button(buttonStyle);
		restartButton.setColor(Color.GREEN);

		restartButton.addListener((Event e) -> {
			if (!isTouchDownEvent(e)) 
				return false;

			instrumental.dispose();
			oceanSurf.dispose();

			SpaceQuadratic.setActiveScreen(new LevelScreen());
			return true;
		});

		ButtonStyle buttonStyle2 = new ButtonStyle();
		Texture buttonTex2 = new Texture(Gdx.files.internal("keys/audio.png"));
		TextureRegion buttonRegion2 = new TextureRegion(buttonTex2);
		buttonStyle2.up = new TextureRegionDrawable(buttonRegion2);
		Button muteButton = new Button(buttonStyle2);
		muteButton.setColor(Color.GREEN);

		muteButton.addListener((Event e) -> {
			if (!isTouchDownEvent(e)) {
				return false;
			}

			audioVolume = 1 - audioVolume;
			instrumental.setVolume(audioVolume);
			oceanSurf.setVolume(audioVolume);

			return true;
		});
		
		uiTable.pad(10); // margem
		uiTable.row().height(50);
		uiTable.add(pointsLabel).top().left().expand();
		uiTable.add(restartButton).top().right();
		uiTable.add(muteButton).top().right();
		
		youLoseLabel = new Label("Você PERDEU!", BaseGame.labelStyle);
		youLoseLabel.setColor(Color.GREEN);
		youLoseLabel.getColor().a = 1;
		youLoseLabel.addAction(Actions.delay(1));
		youLoseLabel.addAction(Actions.after(Actions.fadeIn(1)));
		youLoseLabel.setVisible(false);
		
		uiTable.row();
		uiTable.add(youLoseLabel).colspan(3).expandY();
		
		dialogBox = new DialogBox(0, 0, uiStage);
		dialogBox.setBackgroundColor(Color.TEAL);
		dialogBox.setFontColor(Color.BLUE);
		dialogBox.setDialogSize(600, 100);
		dialogBox.setFontScale(0.80f);
		dialogBox.alignCenter();
		dialogBox.setVisible(false);

		// uiTable.row();
		// uiTable.add(dialogBox).colspan(4);

		uiTable.row();
		uiTable.add().center().expand();

		waterDrop = Gdx.audio.newSound(Gdx.files.internal("sounds/Water_Drop.ogg"));
		instrumental = Gdx.audio.newMusic(Gdx.files.internal("sounds/Master_of_the_Feast.ogg"));
		oceanSurf = Gdx.audio.newMusic(Gdx.files.internal("sounds/Ocean_Waves.ogg"));

		audioVolume = 1.00f;
		instrumental.setLooping(true);
		instrumental.setVolume(audioVolume);

		//instrumental.play();
		oceanSurf.setLooping(true);
		oceanSurf.setVolume(audioVolume);

		//oceanSurf.play();
	}

	private void createNewRock() {
		new Rock(GameMeasures.SPACE_WIDTH.get() + 10, 
				MathUtils.random(10, GameMeasures.SPACE_HEIGHT.get()), mainStage);
	}

	@Override
	public void update(float dt) {		
		if (!this.gameOver) {
			for (BaseActor rockActor : BaseActor.getList(mainStage, "edu.tcc.actors.Rock")) {
				ship.preventOverlap(rockActor);

				if (ship.overlaps(rockActor)) {
					if(ship.isAlive()) {
						ship.deductDamage();
					} else {
						ship.remove();		
						ship.removeHPBar();
						this.gameOver = true;			
						this.youLoseLabel.setVisible(true);						
					}					
				}

				if (((Rock) rockActor).reachedTheLeftSide() && (!gameOver && !win)) {
					((Rock) rockActor).resetPositionAndSize();
					createNewRock();
				}

				for (BaseActor laserActor : BaseActor.getList(mainStage, "edu.tcc.actors.Laser")) {
					if (((Laser) laserActor).isInsideOfScreen()) {
						if (laserActor.overlaps(rockActor)) {
							this.points += ((Rock) rockActor).getDestructionValue();
							rockActor.remove();
							createNewRock();
							
							((Rock) rockActor).resetPositionAndSize();
							laserActor.remove();
						}
					} else {
						laserActor.remove();
					}
				}
			}
		} 	

		pointsLabel.setText("Pontos: " + this.points);
		
		if(this.points > 3000) {
			SpaceQuadratic.setActiveScreen(new BossScreen(ship.getX(), ship.getY(), 
					this.points, this.ship.getCurrentHPValue()));
		}
	}
	
	@Override
	public void dispose() {
		this.space = null;
		this.pointsLabel = null;
		this.youLoseLabel = null;
		this.instrumental.dispose();
		this.dialogBox = null;
		this.oceanSurf.dispose();
		this.ship = null;
	}
}
