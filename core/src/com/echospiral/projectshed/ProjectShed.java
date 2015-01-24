package com.echospiral.projectshed;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.echospiral.projectshed.screen.GameScreen;

public class ProjectShed extends Game {

	private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
	
	@Override
	public void create () {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        screen = new GameScreen(this);
	}

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }
}
