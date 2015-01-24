package com.echospiral.projectshed.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.echospiral.projectshed.world.World;

public class BreakableWall extends Block {

    private Texture wood;
    private Animation animation;
    private float stateTime;

    public BreakableWall(World world, int x, int y) {
        super(world, x, y);
        stateTime = 0F;
        this.wood = new Texture(Gdx.files.internal("wood.png"));

        this.animation = new Animation(0.025f, new Array<TextureRegion>() {{ add(new TextureRegion(wood, 0, 0, world.COLUMN_WIDTH, world.ROW_HEIGHT)); }} );
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.draw(getAnimation().getKeyFrame(stateTime), getX(), getY());
    }

    private Animation getAnimation() {
        return animation;
    }

    @Override
    public Rectangle getRelativeBounds(int dx, int dy) {
        TextureRegion frame = getAnimation().getKeyFrame(stateTime);
        return new Rectangle(getX() + dx, getY() + dy, frame.getRegionWidth(), frame.getRegionHeight());
    }

}
