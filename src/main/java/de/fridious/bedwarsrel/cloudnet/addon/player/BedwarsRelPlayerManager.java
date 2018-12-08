package de.fridious.bedwarsrel.cloudnet.addon.player;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 07.12.18 23:03
 *
 */

import java.util.*;

public class BedwarsRelPlayerManager {

    private List<BedwarsRelPlayer> bedwarsRelPlayers;

    public BedwarsRelPlayerManager() {
        this.bedwarsRelPlayers = new LinkedList<>();
    }

    /**
     * Returns all online BedwarsRelPlayers
     * @return list of all online BedwarsRelPlayers
     */
    public List<BedwarsRelPlayer> getBedwarsRelPlayers() {
        return bedwarsRelPlayers;
    }

    /**
     * Get BedwarsRelPlayer by UUID
     * @param uuid of player
     * @return given BedwarsRelPlayer
     */
    public BedwarsRelPlayer getBedwarsRelPlayer(UUID uuid) {
        for (BedwarsRelPlayer bedwarsRelPlayer : this.bedwarsRelPlayers) {
            if (bedwarsRelPlayer.getUUID().equals(uuid)) return bedwarsRelPlayer;
        }
        return null;
    }

    /**
     * Create and add new BedwarsRelPlayer to list
     * @param uuid of player
     */
    public void createBedwarsRelPlayer(UUID uuid) {
        this.bedwarsRelPlayers.add(new BedwarsRelPlayer(uuid));
    }

    /**
     * Remove BedwarsRel player
     * @param uuid of player
     */
    public void removeBedwarsRelPlayer(UUID uuid) {
        this.bedwarsRelPlayers.remove(getBedwarsRelPlayer(uuid));
    }
}