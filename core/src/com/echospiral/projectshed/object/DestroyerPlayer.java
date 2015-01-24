package com.echospiral.projectshed.object;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.echospiral.projectshed.world.World;

/**
 * A Player that disables most collisions and can destroy walls.
 */
public class DestroyerPlayer extends Player {
    public DestroyerPlayer(World world, int x, int y, Animation moveUpAnimation, Animation moveLeftAnimation,
                         Animation moveRightAnimation, Animation moveDownAnimation) {
        super(world, x, y, moveUpAnimation, moveLeftAnimation, moveRightAnimation, moveDownAnimation);
    }
}
