package com.echospiral.projectshed.object;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.echospiral.projectshed.Direction;
import com.echospiral.projectshed.world.World;

import static com.echospiral.projectshed.Direction.DOWN;

public class Player extends WorldObject {

    private Animation moveUpAnimation;
    private Animation moveLeftAnimation;
    private Animation moveRightAnimation;
    private Animation moveDownAnimation;
    private Direction direction;
    private float stateTime;

    public Player(World world, int x, int y, Animation moveUpAnimation, Animation moveLeftAnimation, Animation moveRightAnimation, Animation moveDownAnimation) {
        super(world, x, y);
        stateTime = 0F;
        direction = DOWN;
        this.moveUpAnimation = moveUpAnimation;
        this.moveLeftAnimation = moveLeftAnimation;
        this.moveRightAnimation = moveRightAnimation;
        this.moveDownAnimation = moveDownAnimation;
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.draw(getAnimation().getKeyFrame(stateTime), getX(), getY());
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);
        if (getDx() != 0 || getDy() != 0) stateTime += delta;
    }

    private Animation getAnimation() {
        switch (getDirection()) {
            case UP:
                return moveUpAnimation;
            case DOWN:
                return moveDownAnimation;
            case LEFT:
                return moveLeftAnimation;
            case RIGHT:
                return moveRightAnimation;
            default:
                return moveDownAnimation;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Rectangle getRelativeBounds(int dx, int dy) {
        TextureRegion frame = getAnimation().getKeyFrame(stateTime);
        return new Rectangle(getX() + dx, getY() + dy, frame.getRegionWidth(), frame.getRegionHeight());
    }

}
