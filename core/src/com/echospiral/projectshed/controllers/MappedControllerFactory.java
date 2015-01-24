package com.echospiral.projectshed.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.mappings.Ouya;

public class MappedControllerFactory {
    public static MappedController getController(Controller controller) {

        String controllerName = controller.getName();
        if (controllerName.toLowerCase().contains("xbox") && controllerName.contains("360") ||
                controllerName.equals("Controller (USB Gamepad Controller)")) {
            return new XBoxMappedController(controller);
        } else if(controllerName.equals(Ouya.ID)) {
            return new OuyaMappedController(controller);
        } else {
            Gdx.app.log("Controllers", "Unrecognized controller: " + controllerName + ".  Using XBox 360 mapping.");
            return new XBoxMappedController(controller);
        }
    }
}
