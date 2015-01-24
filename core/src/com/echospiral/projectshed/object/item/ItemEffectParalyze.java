package com.echospiral.projectshed.object.item;

import com.echospiral.projectshed.object.Player;

/**
 * Created by Rudi on 1/24/2015.
 */
public class ItemEffectParalyze extends ItemEffectLimitedTime {

    public ItemEffectParalyze(float lastsSeconds) {
        super(lastsSeconds);
    }

    @Override
    public boolean applyEffectToObject(Player player) {
        player.stopMovement();
        return true;
    }

    @Override
    public boolean unApplyEffectToObject(Player player) {
        player.activateMovement();
        return true;
    }

    @Override
    public boolean tick(float delta) {
        return false;
    }
}