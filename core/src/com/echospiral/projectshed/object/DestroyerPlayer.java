package com.echospiral.projectshed.object;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.echospiral.projectshed.Role;
import com.echospiral.projectshed.world.World;

import static com.echospiral.projectshed.world.World.COLUMN_WIDTH;
import static com.echospiral.projectshed.world.World.ROW_HEIGHT;

/**
 * A Player that disables most collisions and can destroy walls.
 */
public class DestroyerPlayer extends Player {
    public DestroyerPlayer(World world, int x, int y, Animation moveUpAnimation, Animation moveLeftAnimation,
                         Animation moveRightAnimation, Animation moveDownAnimation) {
        super(world, x, y, moveUpAnimation, moveLeftAnimation, moveRightAnimation, moveDownAnimation);
        this.setRole(Role.DESTROYER);
    }

    @Override
    protected void doCollisions() {
        if (dx != 0 || dy != 0) {
            // Collide with blocks
            boolean hasCollisionHappened = false;
/*
            for (Block b : world.getBlocks().getGroupObjects()) {
                if (getRelativeBounds(getDx(), 0).overlaps(b.getRelativeBounds(0, 0))) {
                    freeX = false;
                }
                if (getRelativeBounds(0, getDy()).overlaps(b.getRelativeBounds(0, 0))) {
                    freeY = false;
                }
            }
*/

        }
    }

    @Override
    public void handleInput() {
        super.handleInput();
        if (null != getController() && getController().getActionButton()) {
            for (WorldObject object : getWorld().getObjects()) {
                if (object instanceof BreakableWall) {
                    BreakableWall wall = (BreakableWall) object;
                    if (getX() + (ROW_HEIGHT / 2) > object.getX()
                            && getY() + (COLUMN_WIDTH / 2) > object.getY()
                            && getX() < object.getX() + COLUMN_WIDTH
                            && getY() < object.getY() + ROW_HEIGHT) {
                        wall.setDestroying(true);
                    } else {
                        wall.setDestroying(false);
                    }
                }
            }
        }
    }
}
