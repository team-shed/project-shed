package com.echospiral.projectshed.object.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.echospiral.projectshed.world.World;

import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.graphics.Color.WHITE;

/**
 * Created by Rudi on 1/24/2015.
 */
public class ItemSkate extends Item {

    private Texture skate;
    private Animation animation;

    public ItemSkate(World world, int x, int y) {
        super(world, x, y, new Animation(0.025f, new Array<TextureRegion>() {{
            add(new TextureRegion(new Texture(Gdx.files.internal("items/rollerskate.png")), 3, 0, 58, 62));
        }}));

        //this.skate = new Texture(Gdx.files.internal("items/rollerskate.png"));
        //this.animation = new Animation(0.025f, new Array<TextureRegion>() {{ add(new TextureRegion(skate, 3, 0, 58, 62)); }} );

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
        spriteBatch.draw(getAnimation().getKeyFrame(stateTime), getX(), getY(), world.COLUMN_WIDTH, world.ROW_HEIGHT);
    }


}
