package com.echospiral.projectshed.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class KeyboardMappedController implements MappedController {
    public float movementMagnitude = 0.5f;

    public KeyboardMappedController() { }

    public KeyboardMappedController(float magnitude) {
        this.movementMagnitude = magnitude;
    }

    @Override
    public float getLeftAxisX() {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            return -movementMagnitude;
        } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            return movementMagnitude;
        }

        return 0;
    }

    @Override
    public float getLeftAxisY() {
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            return movementMagnitude;
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            return -movementMagnitude;
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
        return Gdx.input.isKeyJustPressed(Input.Keys.E);
    }

    @Override
    public boolean getStartButton() { return Gdx.input.isKeyJustPressed(Input.Keys.ENTER); }
}
