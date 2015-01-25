package com.echospiral.projectshed;

import com.badlogic.gdx.utils.Array;
import com.echospiral.projectshed.controllers.MappedController;
import com.echospiral.projectshed.object.Player;

import static com.echospiral.projectshed.Role.PLAYER;

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

    public void swapRoles() {
        Role currentRole = PLAYER;
        MappedController currentPlayerController = null;
        MappedController currentBuilderController = null;
        MappedController currentDestroyerController = null;

        for (Player player : getPlayers()) {
            currentRole = player.getRole();
            switch (currentRole) {
                case PLAYER:
                    currentPlayerController = player.getController();
                    break;
                case BUILDER:
                    currentBuilderController = player.getController();
                    break;
                case DESTROYER:
                    currentDestroyerController = player.getController();
                    break;
            }
        }
        for (Player player : getPlayers()) {
            currentRole = player.getRole();
            switch (currentRole) {
                case PLAYER:
                    player.setController(currentDestroyerController);
                    player.setDx(0);
                    player.setDy(0);
                    break;
                case BUILDER:
                    player.setController(currentPlayerController);
                    player.setDx(0);
                    player.setDy(0);
                    break;
                case DESTROYER:
                    player.setController(currentBuilderController);
                    player.setDx(0);
                    player.setDy(0);
                    break;
            }
        }
    }

    public void clearPlayers() {
        getPlayers().clear();
    }

    public void reassignPlayers() {
        Array<Player> unassignedPlayers = new Array<>(players);
        int i = 0;
        for (MappedController controller : participatingControllers) {
            if (unassignedPlayers.size != 0) {
                unassignedPlayers.removeIndex(i).setController(controller);
            }
        }
    }

    public int getControllerId(MappedController controller) {
        return participatingControllers.indexOf(controller, true) + 1;
    }

}
