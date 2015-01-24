package com.echospiral.projectshed.object;

import com.badlogic.gdx.utils.Array;

/**
 * Created by Rudi on 1/23/2015.
 */
public class WorldObjectsGroup<T extends WorldObject> {

    private Array<T> groupObjects;

    public WorldObjectsGroup() {
        groupObjects = new Array<>();
    }

    public WorldObjectsGroup add(T newObject) {
        groupObjects.add(newObject);
        return this;
    }

    public Array<T> getGroupObjects() {
        return groupObjects;
    }

}
