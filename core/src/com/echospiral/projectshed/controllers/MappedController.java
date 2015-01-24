package com.echospiral.projectshed.controllers;

/*
    Basic interface for getting pad input.  Implement per pad or generically for a configurable pad.
 */
public interface MappedController {
    public float getLeftAxisX();
    public float getLeftAxisY();
    public float getRightAxisX();
    public float getRightAxisY();

    public boolean getActionButton();
    public boolean getStartButton();
}
