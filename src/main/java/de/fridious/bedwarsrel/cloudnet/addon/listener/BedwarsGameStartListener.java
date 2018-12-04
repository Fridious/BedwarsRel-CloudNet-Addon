package de.fridious.bedwarsrel.cloudnet.addon.listener;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 25.11.18 13:53
 *
 */

import de.dytanic.cloudnet.bridge.CloudServer;
import de.fridious.bedwarsrel.cloudnet.addon.BedwarsRelCloudNetAddon;
import io.github.bedwarsrel.events.BedwarsGameStartEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Listen on game start
 */
public class BedwarsGameStartListener implements Listener {

    @EventHandler
    public void onBedwarsGameStart(BedwarsGameStartEvent event) {
        /*
         * Change the game to ingame in CloudNet
         */
        CloudServer.getInstance().changeToIngame();
        /*
         * Set the max players of the server to 100
         */
        CloudServer.getInstance().setMaxPlayersAndUpdate(event.getGame().getMaxPlayers() + BedwarsRelCloudNetAddon.getInstance().getPluginConfig().getSpectatorSlots());
    }
}