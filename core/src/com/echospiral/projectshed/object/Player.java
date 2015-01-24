package com.echospiral.projectshed.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.echospiral.projectshed.Direction;
import com.echospiral.projectshed.Role;
import com.echospiral.projectshed.controllers.MappedController;
import com.echospiral.projectshed.object.item.Item;
import com.echospiral.projectshed.object.item.ItemEffect;
import com.echospiral.projectshed.world.World;

import static com.echospiral.projectshed.Direction.DOWN;
import static com.echospiral.projectshed.Role.PLAYER;
import static java.lang.Math.round;
import static java.lang.Math.signum;

public class Player extends WorldObject {
    private MappedController controller;

    // TODO: PlayTest movement speed
    private int movementSpeed = 4;

    private ItemEffect currentItemEffect;

    private Animation moveUpAnimation;
    private Animation moveLeftAnimation;
    private Animation moveRightAnimation;
    private Animation moveDownAnimation;
    private Direction direction;
    private float stateTime;
    private Role role;

    public Player(World world, int x, int y, Animation moveUpAnimation, Animation moveLeftAnimation,
                  Animation moveRightAnimation, Animation moveDownAnimation) {
        super(world, x, y);
        stateTime = 0F;
        direction = DOWN;
        this.moveUpAnimation = moveUpAnimation;
        this.moveLeftAnimation = moveLeftAnimation;
        this.moveRightAnimation = moveRightAnimation;
        this.moveDownAnimation = moveDownAnimation;
        role = PLAYER;

        currentItemEffect = null;
    }

    public MappedController getController() { return this.controller; }
    public void setController(MappedController controller) {
        this.controller = controller;
    }

    public void handleInput() {
        if (controller != null) {
            float x = controller.getLeftAxisX();
            float y = controller.getLeftAxisY();
            
            //Ignore joy-stick deadzone.
            Vector2 v = new Vector2(x, y);
            if(v.len2() < 0.015f) {
                x = 0; y = 0;
            }

            setDx((int) round((double)movementSpeed * signum(x)));
            setDy(-(int) round((double)movementSpeed * signum(y)));
        }
    }

    public boolean pickUpItem(Item i) {
        if (i == null) return false;
        dropCurrentItem();
        currentItemEffect = i.effect;
        return currentItemEffect.applyEffectToObject(this);
    }

    public boolean dropCurrentItem() {
        if (currentItemEffect != null) {
            currentItemEffect.unApplyEffectToObject(this);
            currentItemEffect = null;
            return true;
        }
        return false;
    }

    @Override
    public void tick(float delta) {
        // Do item effect check
        if (currentItemEffect != null) {
            if (currentItemEffect.tick(delta)) {
                dropCurrentItem();
            }
        }

        handleInput();
        handleCollisions();
        handleAnimations(delta);
        super.tick(delta);
    }

    private void handleCollisions() {
        collideWith(getWorld().getItems(), new WorldObjectsOnCollisionCallback() {
            @Override
            public Boolean call() {
                WorldObject object2 = getObject2();
                if (object2 instanceof Item) {
                    Item item = (Item) object2;
                    item.activate();
                    getWorld().removeObject(item);
                }
                return true;
            }
        });
    }

    private void handleAnimations(float delta) {
        if (getDx() != 0 || getDy() != 0) stateTime += delta;
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {

        spriteBatch.draw(getAnimation().getKeyFrame(stateTime), getX(), getY());
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
        return new Rectangle(getX() + dx, getY() + dy, 64, 64);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public void addToMovementSpeed(int modifier) {
        this.movementSpeed += modifier;
    }

}
