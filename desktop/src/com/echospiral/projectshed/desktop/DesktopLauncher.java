package com.echospiral.projectshed.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.echospiral.projectshed.ProjectShed;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;
        config.title = "Project S.H.E.D.";
		new LwjglApplication(new ProjectShed(), config);
	}
}
