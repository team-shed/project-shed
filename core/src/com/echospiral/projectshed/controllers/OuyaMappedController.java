package com.echospiral.projectshed.controllers;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.mappings.Ouya;

public class OuyaMappedController implements MappedController{
    private Controller controller;

    public OuyaMappedController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public float getLeftAxisX() {
        return controller.getAxis(Ouya.AXIS_LEFT_X);
    }

    @Override
    public float getLeftAxisY() {
        return controller.getAxis(Ouya.AXIS_LEFT_Y);
    }

    @Override
    public float getRightAxisX() {
        return controller.getAxis(Ouya.AXIS_RIGHT_X);
    }

    @Override
    public float getRightAxisY() {
        return controller.getAxis(Ouya.AXIS_RIGHT_Y);
    }

    @Override
    public boolean getActionButton() {
        return controller.getButton(Ouya.BUTTON_A);
    }
}
