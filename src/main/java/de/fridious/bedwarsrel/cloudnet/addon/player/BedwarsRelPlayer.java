package de.fridious.bedwarsrel.cloudnet.addon.player;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 07.12.18 23:01
 *
 */

import org.bukkit.Material;

import java.util.UUID;

/**
 * BedwarsRelPlayer to save informations
 */
public class BedwarsRelPlayer {

    private final UUID uuid;
    private boolean voteBefore, resourceEnableVote;

    public BedwarsRelPlayer(UUID uuid) {
        this.uuid = uuid;
        this.voteBefore = false;
    }

    public UUID getUUID() {
        return uuid;
    }

    public boolean hasVoteBefore() {
        return voteBefore;
    }

    public boolean isResourceEnableVote() {
        return resourceEnableVote;
    }

    public boolean isSameVote(boolean enabled) {
        return this.resourceEnableVote == enabled;
    }

    public void setVoteBefore(boolean voteBefore) {
        this.voteBefore = voteBefore;
    }

    public void setResourceEnableVote(boolean resourceEnableVote) {
        this.resourceEnableVote = resourceEnableVote;
    }
}