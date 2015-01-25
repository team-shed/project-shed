package com.echospiral.projectshed.controllers;

import com.badlogic.gdx.controllers.Controller;

public class XBoxMappedController implements MappedController {
    private Controller controller;

    public XBoxMappedController(Controller controller) {
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

    @Override
    public float getLeftAxisX() {
        return controller.getAxis(XBox360Pad.AXIS_LEFT_X);
    }

    @Override
    public float getLeftAxisY() {
        return controller.getAxis(XBox360Pad.AXIS_LEFT_Y);
    }

    @Override
    public float getRightAxisX() {
        return controller.getAxis(XBox360Pad.AXIS_RIGHT_X);
    }

    @Override
    public float getRightAxisY() {
        return controller.getAxis(XBox360Pad.AXIS_RIGHT_Y);
    }

    @Override
    public boolean getActionButton() {
        return controller.getButton(XBox360Pad.BUTTON_A);
    }

    @Override
    public boolean getStartButton() { return controller.getButton(XBox360Pad.BUTTON_START);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        XBoxMappedController that = (XBoxMappedController) o;

        if (!controller.equals(that.controller)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return controller.hashCode();
    }
}
