package edu.tcc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import edu.tcc.actions.PulseFadeAction;
import edu.tcc.actors.BaseActor;
import edu.tcc.actors.BossShip;
import edu.tcc.actors.DialogBox;
import edu.tcc.actors.Laser;
import edu.tcc.actors.ParallaxBackgroundActor;
import edu.tcc.actors.Ship;
import edu.tcc.actors.Sign;
import edu.tcc.actors.Wall;
import edu.tcc.game.BaseGame;
import edu.tcc.game.SpaceQuadratic;
import edu.tcc.utils.CoefficientTextInputListener;
import edu.tcc.utils.GameMeasures;

public class BossScreen extends BaseScreen {
	private Ship ship;
	private BossShip bossShip;
	private Sign sign;
	private Wall wall;
	private ParallaxBackgroundActor space;
	private float audioVolume;
	private Music instrumental;
	private Music oceanSurf;
	private Label resultMessage;
	private Label pointsLabel;
	private DialogBox dialogBox;
	private long points;
	private Sound waterDrop;
	private boolean gameOver;
	private boolean wallEnabled;
	private String result;
	private Table coefficientLabelsTable;
	private Label coefficientA;
	private Label coefficientB;
	private Label coefficientC;

	private static final int PADDING = 10;
	private final Label labelA = new Label("A = ", BaseGame.labelStyle);
	private final Label labelB = new Label("B = ", BaseGame.labelStyle);
	private final Label labelC = new Label("C = ", BaseGame.labelStyle);
	private float wallStretchValue;

	public BossScreen(float shipPositionX, float shipPositionY, long playerPointsFromPreviousScreen,
			float currentShipHPValue) {
		this.ship = new Ship(shipPositionX, shipPositionY, mainStage);
		this.bossShip = new BossShip(0, 0, mainStage);

		float bossPositionX = GameMeasures.WINDOW_WIDTH.get() - this.bossShip.getWidth() - PADDING;
		float bossPositionY = GameMeasures.WINDOW_HEIGHT.get() - this.bossShip.getHeight() - PADDING;

		this.bossShip.setX(bossPositionX);		
		this.bossShip.setY(bossPositionY);
		
		GameMeasures.X_BOSS_POSITION.set((int) bossPositionX);
		GameMeasures.Y_BOSS_POSITION.set((int) bossPositionY);

		this.ship.configureHpBar();
		this.ship.setCurrentHPValue(currentShipHPValue);

		this.bossShip.configureHpBar();

		this.points = playerPointsFromPreviousScreen;
		this.resultMessage.setVisible(false);
	}

	@Override
	public void initialize() {
		space = new ParallaxBackgroundActor(0, 0, mainStage);
		space.setSpeed(8);
		BaseActor.setWorldBounds(space);

		pointsLabel = new Label("Pontos: ", BaseGame.labelStyle);

		ButtonStyle buttonStyle = new ButtonStyle();
		Texture buttonTex = new Texture(Gdx.files.internal("keys/undo.png"));
		TextureRegion buttonRegion = new TextureRegion(buttonTex);
		buttonStyle.up = new TextureRegionDrawable(buttonRegion);

		Button restartButton = new Button(buttonStyle);
		restartButton.setColor(Color.GREEN);

		restartButton.addListener((Event e) -> {
			if (!isTouchDownEvent(e)) {
				return false;
			}

			instrumental.dispose();
			oceanSurf.dispose();

			SpaceQuadratic.setActiveScreen(new BossScreen(this.ship.getInitialX(), this.ship.getInitialY(), this.points,
					this.ship.getWidth()));

			return true;
		});

		ButtonStyle buttonStyle2 = new ButtonStyle();
		Texture buttonTex2 = new Texture(Gdx.files.internal("keys/audio.png"));
		TextureRegion buttonRegion2 = new TextureRegion(buttonTex2);
		buttonStyle2.up = new TextureRegionDrawable(buttonRegion2);
		Button muteButton = new Button(buttonStyle2);
		muteButton.setColor(Color.GREEN);

		muteButton.addListener((Event e) -> {
			if (!isTouchDownEvent(e))
				return false;

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

		resultMessage = new Label("", BaseGame.labelStyle);
		resultMessage.setColor(Color.GREEN);
		resultMessage.getColor().a = 1;
		resultMessage.addAction(Actions.delay(1));
		resultMessage.addAction(Actions.after(Actions.fadeIn(1)));
		resultMessage.setVisible(true);

		uiTable.row();
		uiTable.add(resultMessage).colspan(3).expandY();

		dialogBox = new DialogBox(0, 0, uiStage);
		dialogBox.setBackgroundColor(Color.TEAL);
		dialogBox.setFontColor(Color.BLUE);

		dialogBox.setX((GameMeasures.WINDOW_WIDTH.get() - GameMeasures.DIALOG_BOX_WIDTH.get()) / 2);
		dialogBox.setY((GameMeasures.WINDOW_HEIGHT.get() - GameMeasures.DIALOG_BOX_HEIGHT.get()) / 2);

		dialogBox.setDialogSize(GameMeasures.DIALOG_BOX_WIDTH.get(), GameMeasures.DIALOG_BOX_HEIGHT.get());
		dialogBox.setFontScale(0.60f);
		dialogBox.alignCenter();
		dialogBox.setVisible(false);

		waterDrop = Gdx.audio.newSound(Gdx.files.internal("sounds/Water_Drop.ogg"));
		instrumental = Gdx.audio.newMusic(Gdx.files.internal("sounds/Master_of_the_Feast.ogg"));
		oceanSurf = Gdx.audio.newMusic(Gdx.files.internal("sounds/Ocean_Waves.ogg"));

		audioVolume = 1.00f;
		instrumental.setLooping(true);
		instrumental.setVolume(audioVolume);
		// Aqui mudei porque estava ouvindo hino XD
		// instrumental.play();
		oceanSurf.setLooping(true);
		oceanSurf.setVolume(audioVolume);
		// Aqui mudei porque estava ouvindo hino XD
		// oceanSurf.play();
	}

	@Override
	public void update(float dt) {
		if (this.wallEnabled) {
			if (this.wall == null) {				
				float bossShipHeightPositonCenter = (this.bossShip.getHeight() / 2) 
						+ this.bossShip.getY(); 				
				
				this.ship.setY(bossShipHeightPositonCenter);
				
				// Literalmente, empurro a nave pra tras ..  estava dando erro, as vezes 
				// a nave ficava travada entre a placa e o muro.
				this.ship.pushBack();
				this.wall = new Wall(this.bossShip.getX() - 50, this.bossShip.getY(), mainStage);
				this.sign = new Sign(this.wall.getX() - 80, this.wall.getY() + 120, mainStage);				
				this.sign.setY(bossShipHeightPositonCenter);
				this.ship.lockUpAndDownMovement();				
								
				// TODO: Puxar de um arquivo TXT ou DAT
				this.sign.setText("O INIMIGO CRIOU UM MURO DE PROTEÇÃO, E BLOQUEOU "
						+ "OS MOVIMENTOS VERTICAIS DE NOSSA NAVE!!!\n"
						+ "(Para atirar sobre o muro, será preciso combinar "
						+ "os elementos A, B e C para gerar um projétil especial. "
						+ "Pressione no teclado a letra correspondente, digite o valor e "
						+ "pressione <ESPAÇO> para lançar)");
			}

			if (this.sign.isVisible()) {
				this.ship.preventOverlap(this.sign);
				boolean shipNearOfSign = ship.isWithinDistance(4, this.sign);

				if (shipNearOfSign) {
					this.dialogBox.setText(this.sign.getText());
					this.dialogBox.setVisible(true);
					this.sign.setViewing(true);
				}

				if (this.sign.isViewing() && !shipNearOfSign) {
					this.dialogBox.setVisible(false);
					this.sign.setVisible(false);
					this.sign.setViewing(false);

					this.sign.remove();

					coefficientLabelsTable = new Table();
					uiTable.add(coefficientLabelsTable);

					this.coefficientA = new Label("", BaseGame.labelStyle);
					this.coefficientB = new Label("", BaseGame.labelStyle);
					this.coefficientC = new Label("", BaseGame.labelStyle);

					coefficientLabelsTable.add(this.labelA).left().expand();
					coefficientLabelsTable.add(this.coefficientA).size(200).center();
					coefficientLabelsTable.add(this.labelB).center().expand();
					coefficientLabelsTable.add(this.coefficientB).size(200).center();
					coefficientLabelsTable.add(this.labelC).right().expand();
					coefficientLabelsTable.add(this.coefficientC).size(200).center();									
				}
			}

			this.wall.adjustWidthAndHeight(40, this.bossShip.getHeight() + this.wallStretchValue);
			this.ship.preventOverlap(this.wall);
		}

		if (!this.gameOver && this.bossShip.isAlive()) {
			for (BaseActor laserActor : BaseActor.getList(mainStage, "edu.tcc.actors.Laser")) {
				if (((Laser) laserActor).isInsideOfScreen()) {
					if (laserActor.overlaps(this.bossShip)) {
						this.bossShip.addAction(new PulseFadeAction());
						this.bossShip.deductDamage();
						laserActor.remove();												
					}

					if (this.wallEnabled) {
						laserActor.preventOverlap(this.wall);
						this.ship.preventOverlap(this.wall);

						if (coefficientA != null && coefficientB != null && coefficientC != null) {
							if (coefficientA.getText().toString().matches("^(-)?[0-9]+$")
									&& coefficientB.getText().toString().matches("^(-)?[0-9]+$")
									&& coefficientC.getText().toString().matches("^(-)?[0-9]+$")) {

								float a = Float.parseFloat((coefficientA.getText().toString()));
								float b = Float.parseFloat((coefficientB.getText().toString()));
								float c = Float.parseFloat((coefficientC.getText().toString()));

								this.ship.setParabolicLaser(a, b, c, this.ship.getY());
								System.out.println("opa");
							} else {
								this.ship.setHorizontalLaser();
							}							
						} 
						if (laserActor.overlaps(this.wall)) {
							laserActor.remove();
						}
					}
				}
			}

			this.ship.preventOverlap(this.bossShip);

			if (this.ship.overlaps(this.bossShip)) {
				this.gameOver = true;
				this.ship.remove();
				this.ship.removeHPBar();
				this.result = "Você PERDEU!";
			}
		} else if (!this.bossShip.isAlive()) {
			this.result = "Você VENCEU!";
			this.gameOver = true;
			this.bossShip.remove();
			this.bossShip.removeHPBar();
		}

		// Se o <sangue> do boss esta na metade, cria a parede
		if ((this.bossShip.getWidth() / 2) > this.bossShip.getCurrentHPValue()) {
			this.wallEnabled = true;
		}

		pointsLabel.setText("Pontos: " + this.points);
		this.resultMessage.setVisible(true);
		this.resultMessage.setText(this.result);

	}

	@Override
	public boolean keyDown(int key) {
		if (this.wallEnabled && !this.gameOver) {
			if (key == Keys.A) {
				this.highlight(this.labelA);
				this.resetLabelColor(this.labelB, this.labelC);

				Gdx.input.getTextInput(new CoefficientTextInputListener(coefficientA), "Coeficiente A", "", "");
				return true;
			}
			if (key == Keys.B) {
				this.highlight(this.labelB);
				this.resetLabelColor(this.labelA, this.labelC);

				Gdx.input.getTextInput(new CoefficientTextInputListener(coefficientB), "Coeficiente B", "", "");
				return true;
			}
			if (key == Keys.C) {
				this.highlight(this.labelC);
				this.resetLabelColor(this.labelB, this.labelA);

				Gdx.input.getTextInput(new CoefficientTextInputListener(coefficientC), "Coeficiente C", "", "");
				return true;
			}
		}

		return false;
	}

	private void resetLabelColor(Label label1, Label label2) {
		label1.setColor(Color.GREEN);
		label2.setColor(Color.GREEN);
	}

	private void highlight(Label label) {
		label.setColor(Color.GRAY);
	}
}