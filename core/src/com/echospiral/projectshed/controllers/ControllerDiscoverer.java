package com.echospiral.projectshed.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.HashSet;
import java.util.Set;

public class ControllerDiscoverer implements ControllerListener, InputProcessor {
    Array<ControllerDiscoveryListener> listeners;
    Set<MappedController> controllerSet = new HashSet<>();

    public ControllerDiscoverer() {
        listeners = new Array<>();

        Controllers.addListener(this);
        Gdx.input.setInputProcessor(this);
    }

    public void addListener(ControllerDiscoveryListener listener) {
        this.listeners.add(listener);
    }

    private void addMappedController(MappedController mappedController) {
        if(!controllerSet.contains(mappedController)) {
            controllerSet.add(mappedController);
            Gdx.app.log("controllerDiscovery", "Found controller: " + mappedController);
            for (ControllerDiscoveryListener listener : this.listeners) {
                listener.controllerFound(mappedController);
            }
        }
    }

    private void foundGamepad(Controller controller) {
        MappedController mc = MappedControllerFactory.getController(controller);
        if(mc != null) {
            addMappedController(mc);
        }
    }

    private boolean foundKeyboard(int keycode) {
        MappedController mc = null;

        switch(keycode) {
            case Input.Keys.A:
            case Input.Keys.W:
            case Input.Keys.S:
            case Input.Keys.D:
            case Input.Keys.E:
                mc = new KeyboardMappedController(KeyboardMappedController.ControlSet.PLAYER_ONE);
                break;
            case Input.Keys.J:
            case Input.Keys.K:
            case Input.Keys.L:
            case Input.Keys.I:
            case Input.Keys.O:
                mc = new KeyboardMappedController(KeyboardMappedController.ControlSet.PLAYER_TWO);
                break;
            case Input.Keys.UP:
            case Input.Keys.DOWN:
            case Input.Keys.LEFT:
            case Input.Keys.RIGHT:
            case Input.Keys.SHIFT_RIGHT:
                mc = new KeyboardMappedController(KeyboardMappedController.ControlSet.PLAYER_THREE);
                break;
        }

        if(mc != null) {
            addMappedController(mc);
            return true;
        }
        return false;
    }

    @Override
    public void connected(Controller controller) {
        foundGamepad(controller);
    }

    @Override
    public void disconnected(Controller controller) {
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        foundGamepad(controller);
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        foundGamepad(controller);
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        foundGamepad(controller);
        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        foundGamepad(controller);
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        foundGamepad(controller);
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        foundGamepad(controller);
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        foundGamepad(controller);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return foundKeyboard(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
