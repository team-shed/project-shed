package com.echospiral.projectshed.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.echospiral.projectshed.ProjectShed;
import com.echospiral.projectshed.controllers.KeyboardMappedController;
import com.echospiral.projectshed.controllers.MappedController;
import com.echospiral.projectshed.controllers.MappedControllerFactory;

import java.util.HashSet;
import java.util.Set;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

/**
 * Lets players assert that they want to play but using their chose input device.
 *
 * Could be modified to allow a Jamestown-style input mapping (in the distant future.....?)
 */
public class InputSetupScreen extends ScreenAdapter implements ControllerListener {
    ProjectShed game;
    GameScreen gameScreen;

    int minSlots = 2;
    int maxSlots = 3;
    Set<Controller> controllerSet = new HashSet<>();
    boolean hasKeyboardPlayer = false;
    Array<MappedController> addedControllers = new Array<>();

    /**
     * Creates a new InputSetupScreen to discover inputs and hand them to the {@Link GameScreen}.
     * @param game
     * @param gameScreen The GameScreen to load after inputs are configured.
     */
    public InputSetupScreen(ProjectShed game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;

        Controllers.addListener(this);
    }

    /**
     * Renders maxSlots controller slots and prompts for player input
     */
    private void renderControllerSlots() {
        /*
        divide screen into maxSlots regions, 1/3 screen width and draw a UI for each input
         */

        ShapeRenderer shapeRenderer = game.getShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();

        float slotHeight = (768-192) / maxSlots;
        Rectangle rect = new Rectangle(341.0f, 96.0f, 341.0f, slotHeight);
        for(int i = 0; i < maxSlots; i++) {
            rect.y = 96.0f + i * slotHeight;
            shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0.2f, 0.2f, 0.2f, 1.0f);
            shapeRenderer.box(rect.x, rect.y + 5, 0, rect.width, rect.height - 5, 0);

            if(i < addedControllers.size) {
                shapeRenderer.setColor(0.2f, 0.6f, 0.2f, 1.0f);
                shapeRenderer.box(rect.x + 10 + addedControllers.get(i).getLeftAxisX() * 5.0f,
                        rect.y + 10 + addedControllers.get(i).getLeftAxisY() * 5.0f, 0,
                        20, 20, 0);
            }
        }

        shapeRenderer.end();
    }

    @Override
    public void render(float deltaTime) {
        if(!hasKeyboardPlayer && (
                Gdx.input.isKeyJustPressed(Input.Keys.ENTER) ||
                Gdx.input.isKeyJustPressed(Input.Keys.E) ||
                Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
                )) {
            addPlayer(new KeyboardMappedController());
            hasKeyboardPlayer = true;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        SpriteBatch spriteBatch = game.getSpriteBatch();
        spriteBatch.begin();
        spriteBatch.end();

        renderControllerSlots();

        // if not enough players, prompt for more on the left side of the screen

        // once we have enough, prompt on the right that it's time to play (and play a sound?)

        // Check for start button on any registered input to load the next screen.
        for(MappedController controller : addedControllers) {
            if (controller.getStartButton()) {
                loadNextScreen();
            }
        }
    }

    private void addPlayer(MappedController controller) {
        //Gdx.app.log("InputSetup", "Player found!" + controller);
        addedControllers.add(controller);
        gameScreen.addPlayerController(controller);
    }

    private void addAllControllers() {
        for(Controller controller : Controllers.getControllers()) {
            addController(controller);
        }
    }

    private void addController(Controller controller) {
        if(!controllerSet.contains(controller)) {
            Gdx.app.log("InputSetup", "Adding controller " + controller.hashCode() + ": " + controller.getName());
            controllerSet.add(controller);
            MappedController mc = MappedControllerFactory.getController(controller);
            if(mc != null) {
                addPlayer(mc);
            }
        }
    }

    private void loadNextScreen() {
        game.setScreen(gameScreen);
    }

    @Override
    public void connected(Controller controller) {
        addController(controller);
    }

    @Override
    public void disconnected(Controller controller) {
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        addController(controller);
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        addController(controller);
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        addController(controller);
        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        addController(controller);
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        addController(controller);
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        addController(controller);
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        addController(controller);
        return false;
    }
}
