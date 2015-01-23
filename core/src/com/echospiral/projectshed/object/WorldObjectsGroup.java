package com.echospiral.projectshed.object;

import java.util.ArrayList;

/**
 * Created by Rudi on 1/23/2015.
 */
public class WorldObjectsGroup {

    private ArrayList<WorldObject> groupObjects;

    public WorldObjectsGroup() {
        groupObjects = new ArrayList<WorldObject>();
    }

    public WorldObjectsGroup add(WorldObject newObject) {
        groupObjects.add(newObject);
        return this;
    }

    public ArrayList<WorldObject> getGroupObjects() {
        return groupObjects;
    }

}
