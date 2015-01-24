package com.echospiral.projectshed.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.echospiral.projectshed.object.*;
import com.echospiral.projectshed.object.item.Item;

public class World {

    public static final int ROW_HEIGHT = 64;
    public static final int COLUMN_WIDTH = 64;

    private Array<WorldObject> objects;
    private WorldObjectsGroup<Block> blocks;
    private WorldObjectsGroup<Item> items;
    private Texture playerTexture;

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
                addObject(generateWorldObject(col.toLowerCase(), this, x, y));
                x++;
            }
            y++;
            x = 0;
        }
    }

    private WorldObject generateWorldObject(String obj, World world, int x, int y) {
        switch(obj.charAt(0)) { // for now assume everything is single char
            case 'o': // our player
                return new Player(world, x * COLUMN_WIDTH, y * ROW_HEIGHT, new Animation(0.025f, new Array<TextureRegion>() {{ add(new TextureRegion(playerTexture, 0, 0, 66, 92)); }} ),
                        new Animation(0.025f, new Array<TextureRegion>() {{ add(new TextureRegion(playerTexture, 0, 0, 66, 92)); }} ),
                        new Animation(0.025f, new Array<TextureRegion>() {{ add(new TextureRegion(playerTexture, 0, 0, 66, 92)); }} ),
                        new Animation(0.025f, new Array<TextureRegion>() {{ add(new TextureRegion(playerTexture, 0, 0, 66, 92)); }} ));

            case 'x': // exit
                return new Exit(world, x * COLUMN_WIDTH, y * ROW_HEIGHT);
            default:
                return new Block(world, x * COLUMN_WIDTH, y * ROW_HEIGHT);

        }

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

}
