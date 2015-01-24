package com.echospiral.projectshed.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.echospiral.projectshed.ProjectShed;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

/**
 * Displays a splash image for a certain amount of time or until a key is pressed.
 */
public class SplashScreen extends ScreenAdapter {
    private ScreenAdapter nextScreen;
    private ProjectShed game;
    private Sprite splashImage;
    private float fadeIn = 0.5f;
    private float fadeOut = 0.5f;
    private float timeout = 1.0f;
    private float elapsedTime = 0.0f;

    /**
     * Sets up a splash screen to fade in, display, and fade out a sprite.
     * @param game The game instance to work with.
     * @param next The screen to load next.
     * @param image The image to display.
     * @param fadeIn Fade the image out from 0 seconds to this elapsed time.
     * @param fadeOut Start fading out the image after this elapsed time.
     * @param duration Finish fading out at this elapsed time.
     */
    public SplashScreen(ProjectShed game, ScreenAdapter next, Sprite image, float fadeIn, float fadeOut, float duration) {
        this.nextScreen = next;
        this.game = game;
        this.splashImage = image;
        this.fadeIn = fadeIn;
        this.fadeOut = fadeOut;
        this.timeout = duration;
    }

    //TODO: add constructor to take a {@Link Texture}, query screen size, and center a new {@Link Sprite}.

    private float calcAlpha() {
        float alpha = 0.0f;
        if(elapsedTime <= fadeIn) {
            // fade up to 1
            alpha = elapsedTime / fadeIn;
        } else if(elapsedTime > fadeIn && elapsedTime <= fadeOut) {
            // maintain 1
            alpha = 1.0f;
        } else if(elapsedTime >= fadeOut && elapsedTime <= timeout) {
            // fade out to 0
            alpha = 1.0f - (elapsedTime - fadeOut) / (timeout - fadeOut);
        } else if(elapsedTime > timeout) {
            // maintain 0
            alpha = 0.0f;
        }

        return alpha;
    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;

        float alpha = calcAlpha();
        splashImage.setAlpha(alpha);

        if(elapsedTime > timeout) {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
            game.setScreen(nextScreen);
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        SpriteBatch spriteBatch = game.getSpriteBatch();
        spriteBatch.begin();
        splashImage.draw(spriteBatch);
        spriteBatch.end();
    }
}
