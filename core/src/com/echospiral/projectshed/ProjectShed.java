package com.echospiral.projectshed;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.echospiral.projectshed.screen.GameScreen;
import com.echospiral.projectshed.screen.SplashScreen;

public class ProjectShed extends Game {

	private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
	
	@Override
	public void create () {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        Sprite splashImage = new Sprite(new Texture("badlogic.jpg"));
        splashImage.setCenter(512, 384);

        GameScreen gameScreen = new GameScreen(this);
        this.setScreen(new SplashScreen(this, gameScreen, splashImage, 0.5f, 1.5f, 2.0f));
	}

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }
}