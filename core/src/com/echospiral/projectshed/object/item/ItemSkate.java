package com.echospiral.projectshed.object.item;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.echospiral.projectshed.world.World;

import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.graphics.Color.WHITE;

/**
 * Created by Rudi on 1/24/2015.
 */
public class ItemSkate extends Item {

    public ItemSkate(World world, int x, int y, Animation animation) {
        super(world, x, y, animation);
        this.setItemEffect(
                new ItemEffectIncreaseSpeed(3, 5)
        );
    }

    @Override
    public void activate() {
        return;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(RED);
        shapeRenderer.rect(getX(), getY(), world.COLUMN_WIDTH, world.ROW_HEIGHT);
    }


}
