package de.fridious.bedwarsrel.cloudnet.addon.listener;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 25.11.18 13:53
 *
 */

import de.dytanic.cloudnet.bridge.CloudServer;
import io.github.bedwarsrel.events.BedwarsGameStartedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BedwarsGameStartedListener implements Listener {

    /*
    Listen on game start of a bedwars game in BedwarsRel
     */
    @EventHandler
    public void onBedwarsGameStarted(BedwarsGameStartedEvent event) {
        /*
        Change the game to ingame in CloudNet
         */
        CloudServer.getInstance().changeToIngame();
        /*
        Set the max players of the server to 100
         */
        CloudServer.getInstance().setMaxPlayersAndUpdate(100);
    }
}