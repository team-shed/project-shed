package com.echospiral.projectshed.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.echospiral.projectshed.world.World;

public class DestroyerGridSelection extends GridSelection {

    public DestroyerGridSelection(World world, DestroyerPlayer following) {
        super(world, 0, 0, new TextureRegion(new Texture(Gdx.files.internal("select_green.png"))), following);
    }

}
