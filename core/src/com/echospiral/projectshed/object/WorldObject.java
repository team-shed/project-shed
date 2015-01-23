package com.echospiral.projectshed.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.echospiral.projectshed.world.World;

import static java.lang.Math.round;

public abstract class WorldObject {

    private World world;
    private int x;
    private int y;
    private int dx;
    private int dy;

    public WorldObject(World world, int x, int y) {
        this.world = world;
        this.x = x;
        this.y = y;
    }

    public void tick(float delta) {
        boolean xFree = true;
        if (dx != 0) {
            for (WorldObject object : world.getObjects()) {
                if (getRelativeBounds(dx, 0).overlaps(object.getRelativeBounds(0, 0))) {
                    xFree = false;
                }
            }
        }
        boolean yFree = true;
        if (dy != 0) {
            for (WorldObject object : world.getObjects()) {
                if (getRelativeBounds(0, dy).overlaps(object.getRelativeBounds(0, 0))) {
                    yFree = false;
                }
            }
        }
        if (xFree) setX(getX() + (int) round((double) dx * delta));
        if (yFree) setY(getY() + (int) round((double) dy * delta));
    }

    public World getWorld() {
        return world;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public abstract void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer);

    public abstract Rectangle getRelativeBounds(int dx, int dy);
}
