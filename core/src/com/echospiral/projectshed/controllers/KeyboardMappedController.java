package com.echospiral.projectshed.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class KeyboardMappedController implements MappedController {
    public enum ControlSet { PLAYER_ONE, PLAYER_TWO, PLAYER_THREE}

    ControlSet controls;

    public float movementMagnitude = 1.0f;

    public KeyboardMappedController() {
        controls = ControlSet.PLAYER_ONE;
    }

    public KeyboardMappedController(float magnitude) {
        this.movementMagnitude = magnitude;
        this.controls = ControlSet.PLAYER_ONE;
    }

    public KeyboardMappedController(ControlSet controls) {
        this.controls = controls;
    }

    public KeyboardMappedController(ControlSet controls, float magnitude) {
        this.movementMagnitude = magnitude;
        this.controls = controls;
    }

    @Override
    public float getLeftAxisX() {
        switch(controls) {
            case PLAYER_ONE:
                if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                    return -movementMagnitude;
                } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                    return movementMagnitude;
                }
                break;
            case PLAYER_TWO:
                if (Gdx.input.isKeyPressed(Input.Keys.J)) {
                    return -movementMagnitude;
                } else if (Gdx.input.isKeyPressed(Input.Keys.L)) {
                    return movementMagnitude;
                }
                break;
            case PLAYER_THREE:
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    return -movementMagnitude;
                } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    return movementMagnitude;
                }
                break;
        }
        return 0;
    }

    @Override
    public float getLeftAxisY() {
        switch(controls) {
            case PLAYER_ONE:
                if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    return -movementMagnitude;
                } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    return movementMagnitude;
                }
                break;
            case PLAYER_TWO:
                if (Gdx.input.isKeyPressed(Input.Keys.I)) {
                    return -movementMagnitude;
                } else if (Gdx.input.isKeyPressed(Input.Keys.K)) {
                    return movementMagnitude;
                }
                break;
            case PLAYER_THREE:
                if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    return -movementMagnitude;
                } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    return movementMagnitude;
                }
                break;
        }

        return 0;
    }

    @Override
    public float getRightAxisX() {
        return 0;
    }

    @Override
    public float getRightAxisY() {
        return 0;
    }

    @Override
    public boolean getActionButton() {
        switch(ControlSet.PLAYER_ONE) {
            case PLAYER_ONE:
                return Gdx.input.isKeyJustPressed(Input.Keys.E);
            case PLAYER_TWO:
                return Gdx.input.isKeyJustPressed(Input.Keys.O);
            case PLAYER_THREE:
                return Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_RIGHT);
        }
        return false;
    }

    @Override
    public boolean getStartButton() {
                return Gdx.input.isKeyJustPressed(Input.Keys.ENTER);
    }

    public boolean getAnyInput() {
        return getActionButton() || getLeftAxisX() != 0 || getLeftAxisY() != 0;
    }
}
