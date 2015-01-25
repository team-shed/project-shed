package com.echospiral.projectshed.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Predicate;
import com.echospiral.projectshed.PlayerManager;
import com.echospiral.projectshed.ProjectShed;
import com.echospiral.projectshed.controllers.MappedController;
import com.echospiral.projectshed.object.Player;
import com.echospiral.projectshed.object.WorldObject;
import com.echospiral.projectshed.world.World;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameScreen extends ScreenAdapter {

    private ProjectShed game;
    private Array<World> worlds;
    private int worldIndex;
    private PlayerManager playerManager;
    private Array<MappedController> playerControllers = new Array<>();
    private OrthographicCamera camera;

    /**
     * Countdown until player roles are swapped.
     */
    private float startingSwapTimer = 10.0f;
    private float swapTimer = 10.0f;

    public GameScreen(ProjectShed game) {
        this.game = game;
        playerManager = new PlayerManager();
        worlds = new Array<>();
        worlds.add(new World(this, "worlds/world1_1.csv"));
        worlds.add(new World(this, "worlds/world1_2.csv"));
        worldIndex = 0;

        for(WorldObject o : getWorld().getObjects().select(new Predicate<WorldObject>() {
            @Override
            public boolean evaluate(WorldObject arg0) {
                return arg0 instanceof Player;
            }
        })) {
            playerManager.addPlayer((Player)o);
        }

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public Camera getCamera() { return camera; }

    public void addPlayerController(MappedController controller) {
        playerManager.assignPlayerController(controller);
    }

    public void checkSwapTimer(float delta) {
        swapTimer -= delta;
        if(swapTimer <= 0.0f || Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) {
            Gdx.app.log("GameScreen", "Swap!");
            playerManager.swapRoles();
            swapTimer = startingSwapTimer;
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        if (getWorld() != null) {
            getWorld().tick(delta);
            camera.position.set(getWorld().getPlayer().getX(), getWorld().getPlayer().getY(), 0);
        }

        checkSwapTimer(delta);
        camera.update();

        SpriteBatch spriteBatch = game.getSpriteBatch();
        ShapeRenderer shapeRenderer = game.getShapeRenderer();
        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();
        if (getWorld() != null) {
            getWorld().render(spriteBatch, shapeRenderer);
        }
        spriteBatch.flush();
        shapeRenderer.end();
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        getWorld().dispose();
    }

    public World getWorld() {
        return worlds.get(worldIndex);
    }

    public void nextLevel() {
        worldIndex++;
    }

}
