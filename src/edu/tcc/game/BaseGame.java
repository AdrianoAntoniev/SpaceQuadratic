package edu.tcc.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import edu.tcc.screens.BaseScreen;

public abstract class BaseGame extends Game {
	private static BaseGame game;
	public static LabelStyle labelStyle;
	public static TextFieldStyle textFieldStyle;
	public static TextButtonStyle textButtonStyle;
	public static LabelStyle titleLabelStyle;
	
	public BaseGame() {
		game = this;
	}
	
	@Override
	public void create() {
		/*			
		FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("assets/OpenSans.ttf"));
		FreeTypeFontParameter fontParameters = new FreeTypeFontParameter();
		fontParameters.size = 48;
		fontParameters.color = Color.WHITE;
		fontParameters.borderWidth = 2;
		fontParameters.borderColor = Color.BLACK;
		fontParameters.borderStraight = true;
		fontParameters.minFilter = TextureFilter.Linear;
		fontParameters.magFilter = TextureFilter.Linear;
		
		BitmapFont customFont = fontGenerator.generateFont(fontParameters);
		
		*/
		InputMultiplexer im = new InputMultiplexer();
		Gdx.input.setInputProcessor(im);
		labelStyle = new LabelStyle();
		BitmapFont myFont = new BitmapFont(Gdx.files.internal("fonts/FontQuadratic.fnt"));		
		labelStyle.font = myFont;
		
		titleLabelStyle = new LabelStyle();
		BitmapFont myFontBigger = new BitmapFont(Gdx.files.internal("fonts/FontQuadratic.fnt"));
		myFontBigger.setScale(2);
		titleLabelStyle.font = myFontBigger;		
		
		textButtonStyle = new TextButtonStyle();
		Texture buttonTex = new Texture(Gdx.files.internal("keys/button.png"));
		NinePatch buttonPatch = new NinePatch(buttonTex, 24,24,24,24);
		textButtonStyle.up = new NinePatchDrawable(buttonPatch);
		textButtonStyle.font = myFont;
		textButtonStyle.fontColor = Color.GRAY;
		
		textFieldStyle = new TextFieldStyle();
		BitmapFont myFontInTextField = new BitmapFont(Gdx.files.internal("fonts/FontQuadratic.fnt"));
		myFontInTextField.setScale(1);
		textFieldStyle.font = myFontInTextField;
	}
	
	public static void setActiveScreen(BaseScreen s) {
		game.setScreen(s);
	}
			
}
