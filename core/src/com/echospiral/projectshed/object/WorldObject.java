package com.echospiral.projectshed.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.echospiral.projectshed.world.World;

public abstract class WorldObject {

//    public static abstract class WorldObjectsOnCollisionCallback implements Callable<Boolean> {
//        private WorldObject object1;
//        private WorldObject object2;
//
//        public WorldObjectsOnCollisionCallback() {
//            this.object1 = null;
//            this.object2 = null;
//        }
//
//        public void setArguments(WorldObject o1, WorldObject o2) {
//            this.object1 = o1;
//            this.object2 = o2;
//        }
//
//        public WorldObject getObject1() {
//            return object1;
//        }
//
//        public WorldObject getObject2() {
//            return object2;
//        }
//
//        public abstract Boolean call();
//    }

    protected World world;
    private int x;
    private int y;
    protected int dx;
    protected int dy;
    // Position of previous frame
    private Rectangle cachedRectangle;

    // By default this is true, can be false to emulate stuff lik dizziness or paralysis
    private boolean doesMove;

    private boolean hasCollidedRight;
    private boolean hasCollidedLeft;
    private boolean hasCollidedTop;
    private boolean hasCollidedBottom;

    protected boolean freeX;
    protected boolean freeY;

    private float stateTime, moveTime;

    public WorldObject(World world, int x, int y) {
        this.world = world;
        this.x = x;
        this.y = y;

        this.cachedRectangle = new Rectangle();

        hasCollidedLeft = false;
        hasCollidedRight = false;
        hasCollidedTop = false;
        hasCollidedBottom = false;

        doesMove = true;
    }

    protected void preUpdate() {
        // Save previous coordinates, useful for collisions
        freeX = true;
        freeY = true;
    }

    public void tick(float delta) {
        preUpdate();

        stateTime += delta;
        if (stateTime - moveTime >= 0.05) {
            moveTime += 0.05;
            doCollisions();
            if (doesMove)
                updateMove(delta);
        }
    }

    protected void doCollisions() {

    }

    protected void updateMove(float delta) {
//        if (!isHorizontallyBlocked())
//            setX(getX() + dx);
//
//        if (!isVerticallyBlocked())
//            setY(getY() + dy);
//
//        resetCollisionFlags();

        if (freeX && dx != 0) {
            setX(getX() + getDx());
        }
        if (freeY && dy != 0) {
            setY(getY() + getDy());
        }
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

//    public boolean collideWith(WorldObject object) {
//        return collideWith(object, null);
//    }

//    public <T extends WorldObjectsOnCollisionCallback> boolean collideWith(WorldObject object, T toCall) {
//        boolean anyCollision = false;
//
//        if (dx != 0 || dy != 0) {
//            if (Intersector.intersectRectangles(
//                    getRelativeBounds(dx, 0),
//                    object.getRelativeBounds(0, 0),
//                    cachedRectangle)) {
//                if (cachedRectangle.width > 0) {
//                    if (dx < 0) {
//                        hasCollidedLeft = true;
//                        object.hasCollidedRight = true;
//                        this.x += cachedRectangle.width;
//                        anyCollision = true;
//                    }
//                    else if (dx > 0) {
//                        hasCollidedRight = true;
//                        object.hasCollidedLeft = true;
//                        this.x -= cachedRectangle.width;
//                        anyCollision = true;
//                    }
//                }
//                if (cachedRectangle.height > 0) {
//                    if (dy < 0) {
//                        hasCollidedBottom = true;
//                        object.hasCollidedTop = true;
//                        this.y += cachedRectangle.height;
//                        anyCollision = true;
//                    }
//                    else if (dy > 0) {
//                        hasCollidedTop = true;
//                        object.hasCollidedBottom = true;
//                        this.y -= cachedRectangle.height;
//                        anyCollision = true;
//                    }
//                }
//            }
//        }
//
//        if (toCall != null) {
//            toCall.setArguments(this, object);
//            toCall.call();
//        }
//
//        return anyCollision;
//    }

//    public boolean collideWith(WorldObjectsGroup<? extends WorldObject> group) {
//        return  this.collideWith(group, null);
//    }

//    public <T extends WorldObjectsOnCollisionCallback> boolean collideWith(WorldObjectsGroup<? extends WorldObject> group, T toCall) {
//        boolean anyCollision = false;
//        boolean anyCollisionWhole = false;
//
//        for (WorldObject o : group.getGroupObjects()) {
//            anyCollision = false;
//
//            if (dx != 0) {
//                if (getRelativeBounds(dx, 0).overlaps(o.getRelativeBounds(0, 0))) {
//                    hasCollidedLeft = dx < 0 || hasCollidedLeft;
//                    hasCollidedRight = dx > 0 || hasCollidedRight;
//                    anyCollision = true;
//                }
//            }
//
//            if (dy != 0) {
//                if (getRelativeBounds(0, dy).overlaps(o.getRelativeBounds(0, 0))) {
//                    hasCollidedBottom = dy < 0 || hasCollidedBottom;
//                    hasCollidedTop = dy < 0 || hasCollidedTop;
//                    anyCollision = true;
//                }
//            }
//
//            if (toCall != null && anyCollision) {
//                toCall.setArguments(this, o);
//                toCall.call();
//            }
//
//            anyCollisionWhole = anyCollisionWhole || anyCollision;
//        }
//
//        return  anyCollisionWhole;
//    }

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

    public abstract boolean isSolid();

    public void stopMovement() {
        doesMove = false;
    }

    public void activateMovement() {
        doesMove = true;
    }
}
