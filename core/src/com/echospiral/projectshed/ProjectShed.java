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
    private GameScreen gameScreen;
    private SplashScreen splashScreen;
	
	@Override
	public void create () {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        Sprite splashImage = new Sprite(new Texture("teamshed.png"));
        splashImage.setCenter(400, 300);

        gameScreen = new GameScreen(this);
        splashScreen = new SplashScreen(this, gameScreen, splashImage, 0.5f, 1.5f, 2.0f);

        boolean useInputSetup = false;
        if(useInputSetup) {
            InputSetupScreen inputSetupScreen = new InputSetupScreen(this, gameScreen);
            this.setScreen(inputSetupScreen);
        } else {
            gameScreen.addPlayerController(new KeyboardMappedController());
            this.setScreen(splashScreen);
        }
    }

    @Override
    public void dispose() {
        gameScreen.dispose();
        splashScreen.dispose();
    }


    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }
}