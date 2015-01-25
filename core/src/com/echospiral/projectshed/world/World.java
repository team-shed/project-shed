package com.echospiral.projectshed.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.echospiral.projectshed.object.*;
import com.echospiral.projectshed.object.item.Item;
import com.echospiral.projectshed.object.item.ItemSkate;
import com.echospiral.projectshed.screen.GameScreen;

import java.util.Random;

import static com.badlogic.gdx.graphics.Texture.TextureFilter.Linear;

public class World {

    public static final int ROW_HEIGHT = 64;
    public static final int COLUMN_WIDTH = 64;

    private GameScreen screen;

    private Texture backgroundTexture;
    private Array<WorldObject> objects;
    private WorldObjectsGroup<Block> blocks;
    private WorldObjectsGroup<Item> items;
    private WorldObjectsGroup<Exit> exits;
    private Texture playerTexture;
    private Player player;
    private BuilderPlayer builderPlayer;
    private BuilderGridSelection builderGridSelection;
    private DestroyerPlayer destroyerPlayer;
    private DestroyerGridSelection destroyerGridSelection;
    private Texture handBuildTexture;
    private Texture handDestroyTexture;

    private int width, height;
    private String name;

    public World(GameScreen screen) {
        this.screen = screen;
        backgroundTexture = new Texture(Gdx.files.internal("background_1.png"));
        backgroundTexture.setFilter(Linear, Linear);
        objects = new Array<>();
        blocks = new WorldObjectsGroup<>();
        items = new WorldObjectsGroup<>();
        exits = new WorldObjectsGroup<>();
        playerTexture = new Texture(Gdx.files.internal("player_spritesheet.png"));
        handBuildTexture = new Texture(Gdx.files.internal("hand_red.png"));
        handDestroyTexture = new Texture(Gdx.files.internal("hand_green.png"));

        width = 1;
        height = 1;
    }

    public World(GameScreen screen, String filename, String name) { // load from .csv file
        this(screen);
        this.name = name;
        String cvsSplitBy = ",";
        int x = 0;
        int y = 0;
        int numPlayers = 3;
        String levelString = Gdx.files.internal(filename).readString();
        Array<String> level = new Array<>(levelString.split("\n"));
        level.reverse();

        int maxWidth = 0;

        for (String line : level) {
            // use comma as separator
            Array<String> worldRow = new Array<>(line.split(cvsSplitBy));

            for (String col: worldRow) {
                WorldObject object = generateWorldObject(col.toLowerCase(), this, x, y);
                if (object != null) addObject(object);
                x++;
            }
            y++;
            if (x > maxWidth)
                maxWidth = x;
            x = 0;
        }

        int halfW = maxWidth / 2;
        int halfH = y / 2;

        Random random = new Random();
        if(numPlayers > 1) {
            builderPlayer = new BuilderPlayer(this, screen,
                    (halfW * COLUMN_WIDTH) + random.nextInt(COLUMN_WIDTH * 3) - COLUMN_WIDTH,
                    (halfH * ROW_HEIGHT) + random.nextInt(ROW_HEIGHT * 3) - ROW_HEIGHT,
                    new Animation(0.0f, new Array<TextureRegion>() {{
                        add(new TextureRegion(handBuildTexture, 4, 2, 56, 60));
                    }}),
                    new Animation(0.0f, new Array<TextureRegion>() {{
                        add(new TextureRegion(handBuildTexture, 4, 2, 56, 60));
                    }}),
                    new Animation(0.0f, new Array<TextureRegion>() {{
                        add(new TextureRegion(handBuildTexture, 4, 2, 56, 60));
                    }}),
                    new Animation(0.0f, new Array<TextureRegion>() {{
                        add(new TextureRegion(handBuildTexture, 4, 2, 56, 60));
                    }}));
            addObject(builderPlayer);
            builderGridSelection = new BuilderGridSelection(this, builderPlayer);
            addObject(builderGridSelection);
        }
        if(numPlayers > 2) {
            destroyerPlayer = new DestroyerPlayer(this, screen,
                    (halfW * COLUMN_WIDTH) + random.nextInt(COLUMN_WIDTH * 3) - COLUMN_WIDTH,
                    (halfH * ROW_HEIGHT) + random.nextInt(ROW_HEIGHT * 3) - ROW_HEIGHT,
                    new Animation(0.0f, new Array<TextureRegion>() {{
                        add(new TextureRegion(handDestroyTexture, 4, 2, 56, 60));
                    }}),
                    new Animation(0.0f, new Array<TextureRegion>() {{
                        add(new TextureRegion(handDestroyTexture, 4, 2, 56, 60));
                    }}),
                    new Animation(0.0f, new Array<TextureRegion>() {{
                        add(new TextureRegion(handDestroyTexture, 4, 2, 56, 60));
                    }}),
                    new Animation(0.0f, new Array<TextureRegion>() {{
                        add(new TextureRegion(handDestroyTexture, 4, 2, 56, 60));
                    }}));
            addObject(destroyerPlayer);
            destroyerGridSelection = new DestroyerGridSelection(this, destroyerPlayer);
            addObject(destroyerGridSelection);
        }
    }

    private WorldObject generateWorldObject(String obj, World world, int x, int y) {
        char o;
        for (char c : obj.toCharArray()) {
            switch (c) {
                // items
                    case '1': // Skate, faster movements
                        return new ItemSkate(world, x * COLUMN_WIDTH, y * ROW_HEIGHT);

                // tiles/rooms/chars/environment:
                case 'o': // our player
                    Player player = new Player(world, x * COLUMN_WIDTH, y * ROW_HEIGHT,
                            new Animation(0.5f, new Array<TextureRegion>() {{
                                add(new TextureRegion(playerTexture, 0, 64, 64, 64));
                                add(new TextureRegion(playerTexture, 64, 64, 64, 64));
                                add(new TextureRegion(playerTexture, 128, 64, 64, 64));
                                add(new TextureRegion(playerTexture, 192, 64, 64, 64));
                            }}),
                            new Animation(0.5f, new Array<TextureRegion>() {{
                                add(new TextureRegion(playerTexture, 0, 128, 64, 64));
                                add(new TextureRegion(playerTexture, 64, 128, 64, 64));
                                add(new TextureRegion(playerTexture, 128, 128, 64, 64));
                                add(new TextureRegion(playerTexture, 192, 128, 64, 64));
                            }}),
                            new Animation(0.5f, new Array<TextureRegion>() {{
                                add(new TextureRegion(playerTexture, 0, 192, 64, 64));
                                add(new TextureRegion(playerTexture, 64, 192, 64, 64));
                                add(new TextureRegion(playerTexture, 128, 192, 64, 64));
                                add(new TextureRegion(playerTexture, 192, 192, 64, 64));
                            }}),
                            new Animation(0.5f, new Array<TextureRegion>() {{
                                add(new TextureRegion(playerTexture, 0, 0, 64, 64));
                                add(new TextureRegion(playerTexture, 64, 0, 64, 64));
                                add(new TextureRegion(playerTexture, 128, 0, 64, 64));
                                add(new TextureRegion(playerTexture, 192, 0, 64, 64));
                            }}));
                    //player.setController(new KeyboardMappedController());
                    this.player = player;
                    return player;
                case 'x': // exit
                    return new Exit(world, x * COLUMN_WIDTH, y * ROW_HEIGHT);
                case 'm': // metal aka unbreakable wall
                    return new UnbreakableWall(world, x * COLUMN_WIDTH, y * ROW_HEIGHT);
                case 'w': // wood aka breakable wall
                    return new BreakableWall(world, x * COLUMN_WIDTH, y * ROW_HEIGHT);
                case 'b':
                    return new Block(world, x * COLUMN_WIDTH, y * ROW_HEIGHT);

                default:
                    return null;
            }

        }
        return null;
    }

    public Array<WorldObject> getObjects() {
        return objects;
    }

    public WorldObjectsGroup<Block> getBlocks() {
        return blocks;
    }

    public WorldObjectsGroup<Item> getItems() {
        return items;
    }

    public WorldObjectsGroup<Exit> getExits() {
        return exits;
    }

    public void addObject(WorldObject object) {
        if (object == null) return;
        if (object.getX() >= width)
            width = object.getX() + (int) object.getRelativeBounds(0, 0).getWidth();
        if (object.getY() >= height)
            height = object.getY() + (int) object.getRelativeBounds(0, 0).getHeight();
        getObjects().add(object);
        if (object instanceof Block) blocks.add((Block) object);
        else if (object instanceof Item) items.add((Item) object);
        else if (object instanceof Exit) exits.add((Exit) object);
    }

    public void removeObject(WorldObject object) {
        getObjects().removeValue(object, true);
        if (object instanceof Block) blocks.remove((Block) object);
        else if (object instanceof Item) items.remove((Item) object);
        else if (object instanceof Exit) exits.remove((Exit) object);
    }

    public void tick(float delta) {
        for (WorldObject object : new Array<>(getObjects())) {
            object.tick(delta);
        }
    }

    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.draw(backgroundTexture, 0, 0, width, height);

        for (WorldObject object : getObjects()) {
            object.render(spriteBatch, shapeRenderer);
        }
        // grim hack to paint the player last so we can see his massive head
        if (player != null) {
            player.render(spriteBatch, shapeRenderer);
        }
        if (builderPlayer != null) {
            builderPlayer.render(spriteBatch, shapeRenderer);
        }
        if (destroyerPlayer != null) {
            destroyerPlayer.render(spriteBatch, shapeRenderer);
        }
    }

    public void dispose() {
        for (WorldObject item: items.getGroupObjects()) {
            if (item instanceof Item) {
                ((Item)item).dispose();
            }
        }
        //music.dispose();
    }

    public Player getPlayer() {
        return player;
    }

    public BuilderPlayer getBuilderPlayer() {
        return builderPlayer;
    }

    public BuilderGridSelection getBuilderGridSelection() {
        return builderGridSelection;
    }

    public DestroyerPlayer getDestroyerPlayer() {
        return destroyerPlayer;
    }

    public DestroyerGridSelection getDestroyerGridSelection() {
        return destroyerGridSelection;
    }

    public GameScreen getScreen() {
        return screen;
    }

    public String getName() { return name; }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
