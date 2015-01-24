package com.echospiral.projectshed.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.echospiral.projectshed.object.Block;
import com.echospiral.projectshed.object.Player;
import com.echospiral.projectshed.object.WorldObject;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class World {

    private Array<WorldObject> objects;

    public World() {
        objects = new Array<>();
    }

    public World(String filename) { // load from .csv file

        objects = new Array<>();

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        int x = 1; // 1-indexed for sanity
        int y = 1;

        try {
            br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] worldRow = line.split(cvsSplitBy);
                y = 0;
                for (String col: worldRow) {
                    objects.add(generateWorldObject(col.toLowerCase(), this, x, y));

                    y++;
                }
                x++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        for (WorldObject obj: objects) {
            System.out.println(obj.getX() + "," + obj.getY() + " " + obj.toString());
        }
    }

    private WorldObject generateWorldObject(String obj, World world, int x, int y) {
        switch(obj.charAt(0)) { // for now assume everything is single char
            case 'o': // our player
                Texture playerTexture = new Texture("p1_front.png");
                return new Player(world, x, y, new Animation(0.025f, new Array<TextureRegion>() {{ add(new TextureRegion(playerTexture, 0, 0, 66, 92)); }} ),
                        new Animation(0.025f, new Array<TextureRegion>() {{ add(new TextureRegion(playerTexture, 0, 0, 66, 92)); }} ),
                        new Animation(0.025f, new Array<TextureRegion>() {{ add(new TextureRegion(playerTexture, 0, 0, 66, 92)); }} ),
                        new Animation(0.025f, new Array<TextureRegion>() {{ add(new TextureRegion(playerTexture, 0, 0, 66, 92)); }} ));

            case 'x': // exit
                return new WorldObject(world, x, y) {
                    @Override
                    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {

                    }

                    @Override
                    public Rectangle getRelativeBounds(int dx, int dy) {
                        return null;
                    }

                    @Override
                    public String toString() {
                        return "EXIT";
                    }
                };
            default:
                return new Block(world, x, y);

        }

    }

    public Array<WorldObject> getObjects() {
        return objects;
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
