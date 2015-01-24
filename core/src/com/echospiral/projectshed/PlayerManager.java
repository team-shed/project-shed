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

    public MappedController getControllerForRole(Role role) {
        int i = role.ordinal() % participatingControllers.size;
        return participatingControllers.get(i);
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
        for (Player player : getPlayers()) {
            switch (player.getRole()) {
                case PLAYER:
                    player.setRole(BUILDER);
                    player.setController(getControllerForRole(BUILDER));
                    break;
                case BUILDER:
                    player.setRole(DESTROYER);
                    player.setController(getControllerForRole(DESTROYER));
                    break;
                case DESTROYER:
                    player.setRole(PLAYER);
                    player.setController(getControllerForRole(PLAYER));
                    break;
            }
        }
    }
}
