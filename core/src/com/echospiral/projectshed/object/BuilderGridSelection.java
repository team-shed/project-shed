package com.echospiral.projectshed.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.echospiral.projectshed.world.World;

public class BuilderGridSelection extends GridSelection {

    public BuilderGridSelection(World world, BuilderPlayer following) {
        super(world, 0, 0, new TextureRegion(new Texture(Gdx.files.internal("select_red.png")), 0, 0, 64, 64), following);
    }

}
