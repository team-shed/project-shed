package com.echospiral.projectshed.object;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.echospiral.projectshed.GameSettings;
import com.echospiral.projectshed.Role;
import com.badlogic.gdx.math.Rectangle;
import com.echospiral.projectshed.screen.GameScreen;
import com.echospiral.projectshed.world.World;

import static com.echospiral.projectshed.world.World.COLUMN_WIDTH;
import static com.echospiral.projectshed.world.World.ROW_HEIGHT;

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

        setMovementSpeed(GameSettings.INITIAL_SPEED_DESTROYER);
    }

    @Override
    protected void doCollisions() {
        // Collide with the camera bounds

        Camera c = gameScreen.getCamera();
        Rectangle screen_rect = new Rectangle(0, 0, c.viewportWidth, c.viewportHeight);
        screen_rect.x = c.position.x - screen_rect.width / 2;
        screen_rect.y = c.position.y - screen_rect.height / 2;

        Rectangle playArea = new Rectangle(0, 0, getWorld().getWidth(), getWorld().getHeight());

        float minx = Math.max(screen_rect.x, playArea.x);
        float miny = Math.max(screen_rect.y, playArea.y);
        float maxx = Math.min(screen_rect.x + screen_rect.width, playArea.x + playArea.width);
        float maxy = Math.min(screen_rect.y + screen_rect.height, playArea.y + playArea.height);

        playArea.x = minx + 5;
        playArea.y = miny + 5;
        playArea.width = maxx - minx - 10;
        playArea.height = maxy - miny - 10;

        int testMargin = 1000;
        Rectangle left = new Rectangle(playArea.x - testMargin, playArea.y - testMargin,
                testMargin, playArea.height + 2*testMargin);
        Rectangle right = new Rectangle(playArea.x + playArea.width, playArea.y - testMargin,
                testMargin, playArea.height + 2*testMargin);
        Rectangle top = new Rectangle(playArea.x - testMargin, playArea.y + playArea.height,
                playArea.width + 2*testMargin, testMargin);
        Rectangle bottom = new Rectangle(playArea.x - testMargin, playArea.y - testMargin,
                playArea.width + 2*testMargin, testMargin);

        int originX = getX() + (COLUMN_WIDTH / 2);
        int originY = getY() + (ROW_HEIGHT / 2);

        if(left.contains(originX + getDx(), originY)) {
            freeX = false;
            setX((int) (playArea.x - (COLUMN_WIDTH / 2)));
        }

        if(right.contains(originX + getDx(), originY)) {
            freeX = false;
            setX((int)(playArea.x + playArea.width - (COLUMN_WIDTH / 2)));
        }

        if(top.contains(originX, originY + getDy())) {
            freeY = false;
            setY((int) (playArea.y + playArea.height - (ROW_HEIGHT / 2)));
        }

        if(bottom.contains(originX, originY + getDy())) {
            freeY = false;
            setY((int) (playArea.y - (ROW_HEIGHT / 2)));
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
