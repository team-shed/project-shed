package com.echospiral.projectshed.object;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.echospiral.projectshed.world.World;

import static com.echospiral.projectshed.world.World.COLUMN_WIDTH;
import static com.echospiral.projectshed.world.World.ROW_HEIGHT;

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

    @Override
    public void handleInput() {
        super.handleInput();
        if (null != getController() && getController().getActionButton()) {
            boolean posFree = true;
            for (WorldObject object : getWorld().getObjects()) {
                if (object == this) continue;
                if (object instanceof BreakableWall) {
                    BreakableWall wall = (BreakableWall) object;
                    if (getX() + (COLUMN_WIDTH / 2) > object.getX()
                            && getY() + (ROW_HEIGHT / 2) > object.getY()
                            && getX() < object.getX() + COLUMN_WIDTH
                            && getY() < object.getY() + ROW_HEIGHT) {
                        wall.setBuilding(true);
                        posFree = false;
                    } else {
                        wall.setBuilding(false);
                    }
                } else if (getX() + (COLUMN_WIDTH / 2) > object.getX()
                        && getY() + (ROW_HEIGHT / 2) > object.getY()
                        && getX() < object.getX() + COLUMN_WIDTH
                        && getY() < object.getY() + ROW_HEIGHT) {
                    posFree = false;
                }
            }
            if (posFree) {
                BreakableWall wall = new BreakableWall(getWorld(), ((getX() + (COLUMN_WIDTH / 2)) / COLUMN_WIDTH) * COLUMN_WIDTH, ((getY() + (ROW_HEIGHT / 2)) / ROW_HEIGHT) * ROW_HEIGHT, 0);
                wall.setBuilding(true);
                getWorld().addObject(wall);
            }
        }
    }
}
