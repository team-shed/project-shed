package com.echospiral.projectshed.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.echospiral.projectshed.ProjectShed;
import com.echospiral.projectshed.controllers.MappedController;
import com.echospiral.projectshed.world.World;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameScreen extends ScreenAdapter {

    private ProjectShed game;
    private World world;
    private Array<MappedController> playerControllers = new Array<MappedController>();

    public GameScreen(ProjectShed game) {
        this.game = game;
        world = new World();
    }

    public void setControllers(Array<MappedController> controllers) {
        this.playerControllers = controllers;

        // TODO: When player roles are set (at game load or on timer event) assign a controller to each one as well.
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
