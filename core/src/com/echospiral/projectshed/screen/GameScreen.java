package com.echospiral.projectshed.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Predicate;
import com.echospiral.projectshed.GameSettings;
import com.echospiral.projectshed.PlayerManager;
import com.echospiral.projectshed.ProjectShed;
import com.echospiral.projectshed.controllers.ControllerDiscoverer;
import com.echospiral.projectshed.controllers.ControllerDiscoveryListener;
import com.echospiral.projectshed.controllers.MappedController;
import com.echospiral.projectshed.object.Player;
import com.echospiral.projectshed.object.WorldObject;
import com.echospiral.projectshed.world.World;

import java.math.BigDecimal;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static java.lang.Math.PI;
import static java.lang.Math.round;

public class GameScreen extends ScreenAdapter {

    private ProjectShed game;
    private Array<World> worlds;
    private int worldIndex;
    private PlayerManager playerManager;
    private Array<MappedController> playerControllers = new Array<>();
    private OrthographicCamera camera;
    private WinScreen winScreen;
    private float cameraScale = 1.0f;
    private BitmapFont font;
    private float fontAlpha;

    private boolean creditsShown;

    ControllerDiscoverer controllerDiscoverer;

    /**
     * Countdown until player roles are swapped.
     */
    private float startingSwapTimer;
    private float swapTimer;

    public GameScreen(ProjectShed game) {
        this.game = game;
        playerManager = new PlayerManager();
        worlds = new Array<>();
        worlds.add(new World(this, "worlds/world1_1.csv", "Get to the spaceship!"));
        worlds.add(new World(this, "worlds/world1_2.csv", "A longer walk"));
        worlds.add(new World(this, "worlds/world1_3.csv", "Diagonal chase"));
        worlds.add(new World(this, "worlds/world1_4.csv", "Through the S"));
        worlds.add(new World(this, "worlds/world1_5.csv", "What do we do now?"));
        worlds.add(new World(this, "worlds/world2_1.csv", "The Room"));
        worlds.add(new World(this, "worlds/world2_2.csv", "Maze X"));
        worlds.add(new World(this, "worlds/world2_3.csv", "Labyrinth"));
        worlds.add(new World(this, "worlds/world2_4.csv", "Wooden Maze"));
        worlds.add(new World(this, "worlds/world2_5.csv", "Choices"));
        worlds.add(new World(this, "worlds/world3_1.csv", "Corridors"));
        worlds.add(new World(this, "worlds/world3_2.csv", "S revisited"));
        worlds.add(new World(this, "worlds/world3_3.csv", "Tiny"));
        worlds.add(new World(this, "worlds/world3_4.csv", "The Massive Maze"));
        worlds.add(new World(this, "worlds/final.csv", "Space..."));
        //worlds.add(new World(this, "worlds/protoworld.csv", "log.debug()"));
        worldIndex = 0;

        startingSwapTimer = GameSettings.INITIAL_SWAP_INTERVAL;
        swapTimer = startingSwapTimer;

        for(WorldObject o : getWorld().getObjects().select(new Predicate<WorldObject>() {
            @Override
            public boolean evaluate(WorldObject arg0) {
                return arg0 instanceof Player;
            }
        })) {
            playerManager.addPlayer((Player)o);
        }

        font = new BitmapFont(false);
        font.setScale(2);

        fontAlpha = 1.0f;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        winScreen = new WinScreen(game);

        /*
        listen for new controllers or keyboard input and add them to the game
         */
        controllerDiscoverer = new ControllerDiscoverer();
        controllerDiscoverer.addListener(new ControllerDiscoveryListener() {
            @Override
            public void controllerFound(MappedController controller) {
                addPlayerController(controller);
            }
        });
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
        if (swapTimer <= 0.0f) {
            playerManager.swapRoles();
            swapTimer += startingSwapTimer;

        }
        else if (GameSettings.debug && Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) {
            Gdx.app.log("GameScreen", "Swap!");
            playerManager.swapRoles();
            swapTimer += startingSwapTimer;
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
            getWorld().render(spriteBatch, shapeRenderer, font);
        }

        drawTimer(spriteBatch);
        drawLevelName(spriteBatch);
        possiblyDrawCredits(spriteBatch);

        spriteBatch.flush();
        shapeRenderer.end();
        spriteBatch.end();
    }

    private void possiblyDrawCredits(SpriteBatch spriteBatch) {
        if (creditsShown) {
            font.setColor(Color.WHITE);
            font.setScale(2);
            font.drawMultiLine(spriteBatch, "Congratulations!\n\n" +
                            "Thanks for playing\n\n" +
                            "Project S.H.E.D.\n\n" +
                    "Team SHED:\n\n" +
                    "Ross - Programming & Art\n" +
                    "Oddvar - Programming\n" +
                    "Jeff - Programming\n" +
                    "Rudi - Programming\n" +
                    "Patrick - Music\n" +
                    "Niclas - Sound effects\n\n" +
                    "Made for The Global Game Jam 2015",
                    camera.position.x - 128, camera.position.y + (camera.viewportHeight / 2) - 8);
        }
    }

    private void drawTimer(SpriteBatch s) {
        if (swapTimer < 4.0) {
            font.setColor(Color.RED.r, Color.RED.g, Color.RED.b, 0.75f);
            font.setScale(12 + (int) (3.0 * (Math.cos(8.0 * 0.5 * PI * new BigDecimal(swapTimer).doubleValue()))));
            font.draw(s, (int) swapTimer + "", camera.position.x - 40, camera.position.y + 110);
        }
        else {
            font.setColor(Color.WHITE);
            font.setScale(2);
            font.draw(s, (round(swapTimer * 10D) / 10D) + "s", camera.position.x - 30, camera.position.y + (camera.viewportHeight / 2) - 8);
        }
    }

    private void drawLevelName(SpriteBatch s) {
        font.setColor(0f, 1.0f, 0f, fontAlpha);
        font.setScale(2);
        if (fontAlpha > 0.003f) {
            fontAlpha -= 0.003f; // fadeout
        }
        else {
            fontAlpha = 0f;
        }
        font.draw(s, worlds.get(worldIndex).getName(), getCamera().position.x - 350, getCamera().position.y - 220);
    }

    private void updateCamera() {
        int mapWidth = getWorld().getWidth();
        int mapHeight = getWorld().getHeight();
        int cameraWidth = (int)getCamera().viewportWidth;
        int cameraHeight = (int)getCamera().viewportHeight;
        Player player = getWorld().getHero();
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

        if (GameSettings.debug) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) {
                cameraScale = (cameraScale + 1) % 6;
                camera.setToOrtho(false, cameraScale * 200, cameraScale * 150);
            } else if (Gdx.input.isKeyJustPressed((Input.Keys.NUM_0))) {
                nextLevel();
            }
        }
        camera.update();
    }

    @Override
    public void dispose() {
        if (getWorld() != null) getWorld().dispose();
        winScreen.dispose();
    }

    public World getWorld() {
        return worlds.size > worldIndex ? worlds.get(worldIndex) : null;
    }

    public void nextLevel() {
        winScreen.resetCountdown();
        winScreen.setNextScreen(this);
        winScreen.setWinner("Player " + getPlayerManager().getControllerId(getWorld().getHero().getController()));
        game.setScreen(winScreen);
        winScreen.playSound();
        getPlayerManager().clearPlayers();
        worldIndex++;
        if (getWorld() != null) {
            getPlayerManager().addPlayer(getWorld().getHero());
            getPlayerManager().addPlayer(getWorld().getBuilderPlayer());
            getPlayerManager().addPlayer(getWorld().getDestroyerPlayer());
            getPlayerManager().reassignPlayers();
            swapTimer = startingSwapTimer; // if you win you get to start the next level
            fontAlpha = 1.0f;
        }
    }

    public boolean isOnLastLevel() {
        return worldIndex == worlds.size - 1;
    }

    public void showCredits() {
        creditsShown = true;
    }

}
