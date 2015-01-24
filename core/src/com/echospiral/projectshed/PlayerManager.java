package com.echospiral.projectshed;

import com.badlogic.gdx.utils.Array;
import com.echospiral.projectshed.object.Player;

import static com.echospiral.projectshed.Role.*;

public class PlayerManager {

    private Array<Player> players;

    public PlayerManager() {
        players = new Array<>();
    }

    public Array<Player> getPlayers() {
        return players;
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
