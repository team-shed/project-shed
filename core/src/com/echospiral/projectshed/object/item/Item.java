package com.echospiral.projectshed.object.item;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.echospiral.projectshed.object.WorldObject;
import com.echospiral.projectshed.world.World;

public abstract class Item extends WorldObject {

    private Animation animation;
    protected float stateTime;
    public ItemEffect effect;

    public Item(World world, int x, int y, Animation animation) {
        super(world, x, y);
        this.animation = animation;
        stateTime = 0F;
        // TODO: subclass will create the right item effect?
        this.effect = null;
    }

    public void setItemEffect(ItemEffect effect) {
        this.effect = effect;
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.draw(getAnimation().getKeyFrame(stateTime), getX(), getY());
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);
        stateTime += delta;
    }

    @Override
    public Rectangle getRelativeBounds(int dx, int dy) {
        //TextureRegion frame = getAnimation().getKeyFrame(stateTime);
        //return new Rectangle(getX() + dx, getY() + dy, frame.getRegionWidth(), frame.getRegionHeight());

        return new Rectangle(getX() + dx, getY() + dy, world.COLUMN_WIDTH, world.ROW_HEIGHT);
    }

    public Animation getAnimation() {
        return animation;
    }

    public abstract void activate();

}
