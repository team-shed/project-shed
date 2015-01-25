package com.echospiral.projectshed.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
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
    private WinScreen winScreen;
    private float cameraScale = 1.0f;

    /**
     * Countdown until player roles are swapped.
     */
    private float startingSwapTimer = 5.0f;
    private float swapTimer = 5.0f;

    public GameScreen(ProjectShed game) {
        this.game = game;
        playerManager = new PlayerManager();
        worlds = new Array<>();
        worlds.add(new World(this, "worlds/world1_1.csv", "Get to the exit!"));
        worlds.add(new World(this, "worlds/world1_2.csv", "A longer walk"));
        worlds.add(new World(this, "worlds/world1_3.csv", "Diagonal chase"));
        worlds.add(new World(this, "worlds/world1_4.csv", "Through the S"));
        worlds.add(new World(this, "worlds/world1_5.csv", "What do we do now?"));
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
        winScreen = new WinScreen(game);
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
            if (getWorld() != null) {
                updateCamera();
            }
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
        int mapWidth = getWorld().getWidth();
        int mapHeight = getWorld().getHeight();
        int cameraWidth = (int)getCamera().viewportWidth;
        int cameraHeight = (int)getCamera().viewportHeight;
        Player player = getWorld().getPlayer();
        Rectangle playerBounds = player.getRelativeBounds(0, 0);
        int playerX = (int)(player.getX() + playerBounds.width / 2);
        int playerY = (int)(player.getY() + playerBounds.height / 2);

        float scale = (float)playerX / mapWidth; // player as 0 to 1
        int travel = (int)(Math.max(0, mapWidth - cameraWidth) * 1.5f); // scale with this
        int from = (mapWidth - travel) / 2;
        int cameraX = from + (int)(scale * travel);

        scale = (float)playerY / mapHeight; // player as 0 to 1
        travel = (int)(Math.max(0, mapHeight - cameraHeight) * 1.5f); // scale with this
        from = (mapHeight - travel) / 2;
        int cameraY = from + (int)(scale * travel);

        camera.position.set(cameraX, cameraY, 0);

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) {
            cameraScale = (cameraScale + 1) % 6;
            camera.setToOrtho(false, cameraScale * 200, cameraScale * 200);
        }
        camera.update();
    }

    @Override
    public void dispose() {
        if (getWorld() != null) getWorld().dispose();
    }

    public World getWorld() {
        return worlds.size > worldIndex ? worlds.get(worldIndex) : null;
    }

    public void nextLevel() {
        winScreen.resetCountdown();
        winScreen.setNextScreen(this);
        winScreen.setWinner("Player " + getPlayerManager().getControllerId(getWorld().getPlayer().getController()));
        game.setScreen(winScreen);
        getPlayerManager().clearPlayers();
        worldIndex++;
        if (getWorld() != null) {
            getPlayerManager().addPlayer(getWorld().getPlayer());
            getPlayerManager().addPlayer(getWorld().getBuilderPlayer());
            getPlayerManager().addPlayer(getWorld().getDestroyerPlayer());
            getPlayerManager().reassignPlayers();
            swapTimer = startingSwapTimer; // if you win you get to start the next level
        }
    }

}
