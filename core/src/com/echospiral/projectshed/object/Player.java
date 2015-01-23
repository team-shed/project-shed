package com.echospiral.projectshed.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.echospiral.projectshed.world.World;

import static com.badlogic.gdx.graphics.Color.CYAN;

public class Player extends WorldObject {

    public Player(World world, int x, int y) {
        super(world, x, y);
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(CYAN);
        shapeRenderer.rect(getX(), getY(), 32, 32);
    }

    @Override
    public Rectangle getRelativeBounds(int dx, int dy) {
        return new Rectangle(getX() + dx, getY() + dy, 32, 32);
    }

}
