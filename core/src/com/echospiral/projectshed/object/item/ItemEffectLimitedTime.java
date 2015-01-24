package com.echospiral.projectshed.object.item;

import com.echospiral.projectshed.object.Player;

/**
 * Created by Rudi on 1/24/2015.
 */
public abstract class ItemEffectLimitedTime extends ItemEffect {

    private float lastsInSeconds;
    private float currentTimeSeconds;

    public ItemEffectLimitedTime(float lastsInSeconds) {
        this.lastsInSeconds = lastsInSeconds;
        this.currentTimeSeconds = 0;
    }

    @Override
    public boolean applyEffectToObject(Player player) {
        return false;
    }

    @Override
    public boolean unApplyEffectToObject(Player player) {
        return false;
    }

    @Override
    public boolean tick(float delta) {
        currentTimeSeconds += delta;
        if (currentTimeSeconds >= lastsInSeconds) {
            return true;
        }
        return false;
    }
}
