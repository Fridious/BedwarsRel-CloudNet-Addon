package de.fridious.bedwarsrel.cloudnet.addon.listener;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 04.12.18 14:33
 *
 */

import de.fridious.bedwarsrel.cloudnet.addon.BedwarsRelCloudNetAddon;
import io.github.bedwarsrel.events.BedwarsGameOverEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Listen on game end
 */
public class BedwarsGameOverListener implements Listener {

    @EventHandler
    public void onBedwarsGameOver(BedwarsGameOverEvent event) {
        /*
         * Add reward for the winners
         */
        if(event.getWinner() != null)
            event.getWinner().getPlayers().forEach((player)-> BedwarsRelCloudNetAddon.getInstance().getEconomy().depositPlayer(player, BedwarsRelCloudNetAddon.getInstance().getPluginConfig().getWinReward()));
    }
}