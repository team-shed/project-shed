package com.echospiral.projectshed.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import static java.lang.Math.round;
import com.echospiral.projectshed.controllers.MappedController;
import com.echospiral.projectshed.world.World;

import static com.badlogic.gdx.graphics.Color.CYAN;

public class Player extends WorldObject {
    private MappedController controller;
    // TODO: PlayTest movement speed
    private int movementSpeed = 5;

    public Player(World world, int x, int y) {
        super(world, x, y);
    }

    public void setController(MappedController controller) {
        this.controller = controller;
    }

    public void handleInput() {
        setDx((int)round((double)movementSpeed * controller.getLeftAxisX()));
        setDy((int)round((double)movementSpeed * controller.getLeftAxisY()));
    }

    @Override
    public void tick(float delta) {
        handleInput();

        super.tick(delta);
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
