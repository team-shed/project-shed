package com.echospiral.projectshed.object;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.echospiral.projectshed.object.Player;
import com.echospiral.projectshed.object.item.Item;
import com.echospiral.projectshed.world.World;

/**
 * A Player that disables most collisions and can build walls.
 */
public class BuilderPlayer extends Player {
    public BuilderPlayer(World world, int x, int y, Animation moveUpAnimation, Animation moveLeftAnimation,
                  Animation moveRightAnimation, Animation moveDownAnimation) {
        super(world, x, y, moveUpAnimation, moveLeftAnimation, moveRightAnimation, moveDownAnimation);
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
}
