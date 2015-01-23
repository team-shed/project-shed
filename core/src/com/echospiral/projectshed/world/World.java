package com.echospiral.projectshed.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.echospiral.projectshed.object.WorldObject;

public class World {

    private Array<WorldObject> objects;

    public World() {
        objects = new Array<>();
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
