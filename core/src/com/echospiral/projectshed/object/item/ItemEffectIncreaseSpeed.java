package com.echospiral.projectshed.object.item;

import com.badlogic.gdx.Gdx;
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
        player.setMovementSpeed(player.getMovementSpeed() * speedModifier);
        return true;
    }

    @Override
    public boolean unApplyEffectToObject(Player player) {
        player.setMovementSpeed(player.getMovementSpeed() / speedModifier);
        Gdx.app.log("ItemEffectIncreasedSpeed", "Finished speed effect");
        return true;
    }

}
