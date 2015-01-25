package com.echospiral.projectshed.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.echospiral.projectshed.world.World;

import static com.echospiral.projectshed.world.World.COLUMN_WIDTH;
import static com.echospiral.projectshed.world.World.ROW_HEIGHT;

public class GridSelection extends WorldObject {

    private Player following;
    private TextureRegion texture;

    public GridSelection(World world, int x, int y, TextureRegion texture, Player following) {
        super(world, x, y);
        this.following = following;
        this.texture = texture;
    }

    @Override
    public void tick(float delta) {
        setX(((following.getX() + (COLUMN_WIDTH / 2)) / COLUMN_WIDTH) * COLUMN_WIDTH);
        setY(((following.getY() + (ROW_HEIGHT / 2)) / ROW_HEIGHT) * ROW_HEIGHT);
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.draw(texture, getX(), getY());
    }

    @Override
    public Rectangle getRelativeBounds(int dx, int dy) {
        return null;
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
