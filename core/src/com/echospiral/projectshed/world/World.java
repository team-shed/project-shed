package com.echospiral.projectshed.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.echospiral.projectshed.controllers.KeyboardMappedController;
import com.echospiral.projectshed.object.*;
import com.echospiral.projectshed.object.item.Item;

public class World {

    public static final int ROW_HEIGHT = 64;
    public static final int COLUMN_WIDTH = 64;

    private Array<WorldObject> objects;
    private WorldObjectsGroup<Block> blocks;
    private WorldObjectsGroup<Item> items;
    private Texture playerTexture;
    private Player player;

    public World() {
        objects = new Array<>();
        blocks = new WorldObjectsGroup<>();
        items = new WorldObjectsGroup<>();
        playerTexture = new Texture(Gdx.files.internal("p1_stand.png"));
    }

    public World(String filename) { // load from .csv file
        this();
        String cvsSplitBy = ",";
        int x = 0;
        int y = 0;
        String levelString = Gdx.files.internal(filename).readString();
        Array<String> level = new Array<>(levelString.split("\n"));
        level.reverse();

        for (String line : level) {
            // use comma as separator
            Array<String> worldRow = new Array<>(line.split(cvsSplitBy));

            for (String col: worldRow) {
                WorldObject object = generateWorldObject(col.toLowerCase(), this, x, y);
                if (object != null) addObject(object);
                x++;
            }
            y++;
            x = 0;
        }
    }

    private WorldObject generateWorldObject(String obj, World world, int x, int y) {
        char o;
        for (char c : obj.toCharArray()) {
            switch (c) {
                // items
                //     case '1': items.add(new BananaSkin(world, x, y));
                //       break;


                // tiles/rooms/chars/environment:
                case 'o': // our player
                    Player player = new Player(world, x * COLUMN_WIDTH, y * ROW_HEIGHT,
                            new Animation(0.025f, new Array<TextureRegion>() {{
                                add(new TextureRegion(playerTexture, 0, 0, 66, 92));
                            }}),
                            new Animation(0.025f, new Array<TextureRegion>() {{
                                add(new TextureRegion(playerTexture, 0, 0, 66, 92));
                            }}),
                            new Animation(0.025f, new Array<TextureRegion>() {{
                                add(new TextureRegion(playerTexture, 0, 0, 66, 92));
                            }}),
                            new Animation(0.025f, new Array<TextureRegion>() {{
                                add(new TextureRegion(playerTexture, 0, 0, 66, 92));
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

    public void addObject(WorldObject object) {
        if (object == null) return;
        getObjects().add(object);
        if (object instanceof Block) blocks.add((Block) object);
        if (object instanceof Item) items.add((Item) object);
    }

    public void removeObject(WorldObject object) {
        getObjects().removeValue(object, true);
        if (object instanceof Block) blocks.remove((Block) object);
        if (object instanceof Item) items.remove((Item) object);
    }

    public void tick(float delta) {
        for (WorldObject object : getObjects()) {
            object.tick(delta);
        }
    }

    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        for (WorldObject object : getObjects()) {
            object.render(spriteBatch, shapeRenderer);
        }
    }

    public Player getPlayer() {
        return player;
    }

}
