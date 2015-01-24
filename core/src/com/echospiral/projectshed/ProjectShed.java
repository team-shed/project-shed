package com.echospiral.projectshed;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.echospiral.projectshed.controllers.KeyboardMappedController;
import com.echospiral.projectshed.screen.GameScreen;
import com.echospiral.projectshed.screen.InputSetupScreen;
import com.echospiral.projectshed.screen.SplashScreen;

public class ProjectShed extends Game {

	private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
	
	@Override
	public void create () {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        Sprite splashImage = new Sprite(new Texture("teamshed.png"));
        splashImage.setCenter(400, 300);

        GameScreen gameScreen = new GameScreen(this);

        boolean useInputSetup = false;
        if(useInputSetup) {
            InputSetupScreen inputSetupScreen = new InputSetupScreen(this, gameScreen);
            this.setScreen(inputSetupScreen);
        } else {
            gameScreen.addPlayerController(new KeyboardMappedController());
            //this.setScreen(gameScreen);
        }
        this.setScreen(new SplashScreen(this, gameScreen, splashImage, 0.5f, 1.5f, 2.0f));
	}

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }
}