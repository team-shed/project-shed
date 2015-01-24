package com.echospiral.projectshed.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.echospiral.projectshed.PlayerManager;
import com.echospiral.projectshed.ProjectShed;
import com.echospiral.projectshed.world.World;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameScreen extends ScreenAdapter {

    private ProjectShed game;
    private World world;
    private PlayerManager playerManager;

    public GameScreen(ProjectShed game) {
        this.game = game;
        playerManager = new PlayerManager();
        world = new World("worlds/world1_1.csv");

    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        SpriteBatch spriteBatch = game.getSpriteBatch();
        ShapeRenderer shapeRenderer = game.getShapeRenderer();
        spriteBatch.begin();
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();
        if (world != null) {
            world.tick(delta);
            world.render(spriteBatch, shapeRenderer);
        }
        shapeRenderer.end();
        spriteBatch.end();
    }
}
