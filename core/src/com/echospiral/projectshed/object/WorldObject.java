package com.echospiral.projectshed.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.echospiral.projectshed.world.World;

import java.util.concurrent.Callable;

import static java.lang.Math.round;

public abstract class WorldObject {

    public static abstract class WorldObjectsOnCollisionCallback implements Callable<Boolean> {
        private WorldObject object1;
        private WorldObject object2;

        public WorldObjectsOnCollisionCallback() {
            this.object1 = null;
            this.object2 = null;
        }

        public void setArguments(WorldObject o1, WorldObject o2) {
            this.object1 = o1;
            this.object2 = o2;
        }

        public WorldObject getObject1() {
            return object1;
        }

        public WorldObject getObject2() {
            return object2;
        }

        public abstract Boolean call();
    }

    protected World world;
    private int x;
    private int y;
    private int dx;
    private int dy;

    private boolean hasCollidedRight;
    private boolean hasCollidedLeft;
    private boolean hasCollidedTop;
    private boolean hasCollidedBottom;

    public WorldObject(World world, int x, int y) {
        this.world = world;
        this.x = x;
        this.y = y;

        hasCollidedLeft = false;
        hasCollidedRight = false;
        hasCollidedTop = false;
        hasCollidedBottom = false;
    }

    public void tick(float delta) {
        updateMove(delta);
    }

    public void updateMove(float delta) {
        if (!isHorizontallyBlocked())
            setX(getX() + (int) round((double) dx * delta));

        if (!isVerticallyBlocked())
            setY(getY() + (int) round((double) dy * delta));

        resetCollisionFlags();
    }

    protected boolean isHorizontallyBlocked() {
        return hasCollidedLeft || hasCollidedRight;
    }

    protected  boolean isVerticallyBlocked() {
        return  hasCollidedBottom || hasCollidedTop;
    }

    protected void resetCollisionFlags() {
        hasCollidedLeft = false;
        hasCollidedRight = false;
        hasCollidedTop = false;
        hasCollidedBottom = false;
    }

    public boolean collideWith(WorldObject object) {
        return  collideWith(object, null);
    }

    public <T extends WorldObjectsOnCollisionCallback> boolean collideWith(WorldObject object, T toCall) {
        boolean anyCollision = false;
        if (dx != 0) {
            if (getRelativeBounds(dx, 0).overlaps(object.getRelativeBounds(0, 0))) {
                hasCollidedLeft = dx < 0 || hasCollidedLeft;
                hasCollidedRight = dx > 0 || hasCollidedRight;
                anyCollision = true;
            }
        }

        if (dy != 0) {
            if (getRelativeBounds(0, dy).overlaps(object.getRelativeBounds(0, 0))) {
                hasCollidedBottom = dy < 0 || hasCollidedBottom;
                hasCollidedTop = dy > 0 || hasCollidedTop;
                anyCollision = true;
            }
        }

        if (toCall != null) {
            toCall.setArguments(this, object);
            toCall.call();
        }

        return anyCollision;
    }

    public boolean collideWith(WorldObjectsGroup<? extends WorldObject> group) {
        return  this.collideWith(group, null);
    }

    public <T extends WorldObjectsOnCollisionCallback> boolean collideWith(WorldObjectsGroup<? extends WorldObject> group, T toCall) {
        boolean anyCollision = false;
        boolean anyCollisionWhole = false;

        for (WorldObject o : group.getGroupObjects()) {
            anyCollision = false;

            if (dx != 0) {
                if (getRelativeBounds(dx, 0).overlaps(o.getRelativeBounds(0, 0))) {
                    hasCollidedLeft = dx < 0 || hasCollidedLeft;
                    hasCollidedRight = dx > 0 || hasCollidedRight;
                    anyCollision = true;
                }
            }

            if (dy != 0) {
                if (getRelativeBounds(0, dy).overlaps(o.getRelativeBounds(0, 0))) {
                    hasCollidedBottom = dy < 0 || hasCollidedBottom;
                    hasCollidedTop = dy < 0 || hasCollidedTop;
                    anyCollision = true;
                }
            }

            if (toCall != null && anyCollision) {
                toCall.setArguments(this, o);
                toCall.call();
            }

            anyCollisionWhole = anyCollisionWhole || anyCollision;
        }

        return  anyCollisionWhole;
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
