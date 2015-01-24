package com.echospiral.projectshed.object.item;

import com.echospiral.projectshed.object.Player;
import com.echospiral.projectshed.object.WorldObject;
import com.echospiral.projectshed.world.World;

/**
 * Created by Rudi on 1/24/2015.
 */
public abstract class ItemEffect {

    public abstract boolean applyEffectToObject(Player player);

    public abstract boolean unApplyEffectToObject(Player player);

    /*
        Returns true if the effect should expire
     */
    public abstract boolean tick(float delta);

}
