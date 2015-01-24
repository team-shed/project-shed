package com.echospiral.projectshed.object.item;

import com.echospiral.projectshed.object.Player;

/**
 * Created by Rudi on 1/24/2015.
 */
public class ItemEffectIncreaseSpeed extends ItemEffectLimitedTime {

    private int speedModifier;

    public ItemEffectIncreaseSpeed(int speedMod, float lastsInSeconds) {
        super(lastsInSeconds);
        this.speedModifier = speedMod;
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

}
