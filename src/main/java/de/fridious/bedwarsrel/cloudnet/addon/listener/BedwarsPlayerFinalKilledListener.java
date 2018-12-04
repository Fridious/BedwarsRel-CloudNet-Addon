package de.fridious.bedwarsrel.cloudnet.addon.listener;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 04.12.18 17:08
 *
 */

import de.fridious.bedwarsrel.cloudnet.addon.BedwarsRelCloudNetAddon;
import de.fridious.bedwarsrel.cloudnet.addon.events.BedwarsPlayerFinalKilledEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Listen on final player kill
 */
public class BedwarsPlayerFinalKilledListener implements Listener {

    @EventHandler
    public void onBedwarsPlayerFinalKilled(BedwarsPlayerFinalKilledEvent event) {
        /*
         * Add reward for the winner
         */
        if(event.getKiller() != null)
            BedwarsRelCloudNetAddon.getInstance().getEconomy().depositPlayer(event.getKiller(), BedwarsRelCloudNetAddon.getInstance().getPluginConfig().getFinalKillReward());
    }
}