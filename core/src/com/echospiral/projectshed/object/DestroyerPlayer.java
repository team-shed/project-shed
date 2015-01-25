package com.echospiral.projectshed.object;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.echospiral.projectshed.Role;
import com.echospiral.projectshed.screen.GameScreen;
import com.echospiral.projectshed.world.World;

/**
 * A Player that disables most collisions and can destroy walls.
 */
public class DestroyerPlayer extends Player {
    GameScreen gameScreen;

    public DestroyerPlayer(World world, GameScreen gameScreen, int x, int y, Animation moveUpAnimation, Animation moveLeftAnimation,
                         Animation moveRightAnimation, Animation moveDownAnimation) {
        super(world, x, y, moveUpAnimation, moveLeftAnimation, moveRightAnimation, moveDownAnimation);
        this.setRole(Role.DESTROYER);

        this.gameScreen = gameScreen;
    }

    @Override
    protected void doCollisions() {
        // Collide with the camera bounds

        Camera c = gameScreen.getCamera();
        Rectangle screen_rect = new Rectangle(0, 0, c.viewportWidth, c.viewportHeight);
        screen_rect.x = c.position.x - screen_rect.width / 2;
        screen_rect.y = c.position.y - screen_rect.height / 2;

        int testMargin = 1000;
        Rectangle left = new Rectangle(screen_rect.x - testMargin, screen_rect.y - testMargin,
                testMargin, screen_rect.height + 2*testMargin);
        Rectangle right = new Rectangle(screen_rect.x + screen_rect.width, screen_rect.y - testMargin,
                testMargin, screen_rect.height + 2*testMargin);
        Rectangle top = new Rectangle(screen_rect.x - testMargin, screen_rect.y + screen_rect.height,
                screen_rect.width + 2*testMargin, testMargin);
        Rectangle bottom = new Rectangle(screen_rect.x - testMargin, screen_rect.y - testMargin,
                screen_rect.width + 2*testMargin, testMargin);

        Rectangle bounds = getRelativeBounds(0, 0);

        if(getRelativeBounds(getDx(), 0).overlaps(left)) {
            freeX = false;
            setX((int) (screen_rect.x));
        }

        if(getRelativeBounds(getDx(), 0).overlaps(right)) {
            freeX = false;
            setX((int)(screen_rect.x + screen_rect.width - bounds.width));
        }

        if(getRelativeBounds(0, getDy()).overlaps(top)) {
            freeY = false;
            setY((int) (screen_rect.y + screen_rect.height - bounds.height));
        }

        if(getRelativeBounds(0, getDy()).overlaps(bottom)) {
            freeY = false;
            setY((int) (screen_rect.y));
        }
    }

    @Override
    public void handleInput() {
        super.handleInput();
        if (null != getController() && getController().getActionButton()) {
            for (WorldObject object : getWorld().getObjects()) {
                if (object instanceof BreakableWall) {
                    BreakableWall wall = (BreakableWall) object;
                    if (getWorld().getDestroyerGridSelection().getX() == object.getX()
                            && getWorld().getDestroyerGridSelection().getY() == object.getY()) {
                        wall.setDestroying(true);
                    } else {
                        wall.setDestroying(false);
                    }
                }
            }
        }
    }
}
