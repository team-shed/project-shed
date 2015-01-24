package com.echospiral.projectshed;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.echospiral.projectshed.controllers.MappedController;
import com.echospiral.projectshed.object.Player;

import static com.echospiral.projectshed.Role.*;

public class PlayerManager {

    private Array<Player> players;
    private Array<MappedController> participatingControllers;

    public PlayerManager() {
        players = new Array<>();
        participatingControllers = new Array<>();
    }

    public Array<Player> getPlayers() {
        return players;
    }

    public void assignPlayerController(MappedController controller) {
        for(Player player : players) {
            if(player.getController() == null) {
                player.setController(controller);
                participatingControllers.add(controller);
                return;
            }
        }
    }
    
    public void addPlayer(Player player) {
        getPlayers().add(player);
    }

    public void removePlayer(Player player) {
        getPlayers().removeValue(player, true);
    }

    public void setupInitialRoles() {
        Role currentRole = PLAYER;
        for (Player player : getPlayers()) {
            player.setRole(currentRole);
            switch (currentRole) {
                case PLAYER:
                    currentRole = BUILDER;
                    break;
                case BUILDER:
                    currentRole = DESTROYER;
                    break;
                case DESTROYER:
                    currentRole = PLAYER;
                    break;
            }
        }
    }

    public void swapRoles() {
        Array<MappedController> controllers = new Array<>();
        for(Player player : getPlayers()) {
            controllers.add(player.getController());
        }

        for (Player player : getPlayers()) {
            MappedController c = controllers.random();
            controllers.removeValue(c, true);
            player.setController(c);
            player.setDx(0); player.setDy(0);

            switch (player.getRole()) {
                case PLAYER:
                    player.setRole(BUILDER);
                    break;
                case BUILDER:
                    player.setRole(DESTROYER);
                    break;
                case DESTROYER:
                    player.setRole(PLAYER);
                    break;
            }
        }
    }
}
