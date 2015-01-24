package com.echospiral.projectshed.object.item;

import com.echospiral.projectshed.object.Player;

/**
 * Created by Rudi on 1/24/2015.
 */
public class ItemEffectIncreaseSpeed extends ItemEffect{

    private int speedModifier;
    private float lastsInSeconds;
    private float currentTimeSeconds;

    public ItemEffectIncreaseSpeed(int speedMod, float lastsInSeconds) {
        this.speedModifier = speedMod;
        this.lastsInSeconds = lastsInSeconds;
        this.currentTimeSeconds = 0;
    }

    @Override
    public boolean applyEffectToObject(Player player) {
        player.addToMovementSpeed(speedModifier);
        return true;
    }

    @Override
    public boolean unApplyEffectToObject(Player player) {
        player.addToMovementSpeed(-speedModifier);
        return true;
    }

    @Override
    public boolean tick(float delta) {
        currentTimeSeconds += delta;
        if (currentTimeSeconds >= lastsInSeconds){
            return true;
        }
        return false;
    }
}
