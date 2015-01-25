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

            updateCamera();
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

    private void updateCamera() {
        int mapWidth = 9 * 64;
        int mapHeight = 9 * 64;
        int cameraWidth = (int)getCamera().viewportWidth;
        int cameraHeight = (int)getCamera().viewportHeight;

        int scale = getWorld().getPlayer().getX() / mapWidth; // player as 0 to 1
        int travel = Math.max(0, mapWidth - cameraWidth); // scale this
        int from = (mapWidth - travel) / 2;
        int cameraX = from + scale * travel;

        scale = getWorld().getPlayer().getY() / mapHeight; // player as 0 to 1
        travel = Math.max(0, mapHeight - cameraHeight); // scale this
        from = (mapHeight - travel) / 2;
        int cameraY = from + scale * travel;

        camera.position.set(cameraX, cameraY, 0);
    }

    @Override
    public void dispose() {
        getWorld().dispose();
    }

    public World getWorld() {
        return worlds.size > worldIndex ? worlds.get(worldIndex) : null;
    }

    public void nextLevel() {
        getPlayerManager().clearPlayers();
        worldIndex++;
        getPlayerManager().addPlayer(getWorld().getPlayer());
        getPlayerManager().addPlayer(getWorld().getBuilderPlayer());
        getPlayerManager().addPlayer(getWorld().getDestroyerPlayer());
        getPlayerManager().reassignPlayers();
    }

}
