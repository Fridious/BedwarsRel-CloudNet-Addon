package de.fridious.bedwarsrel.cloudnet.addon.listener;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 08.12.18 17:37
 *
 */

import de.fridious.bedwarsrel.cloudnet.addon.BedwarsRelCloudNetAddon;
import io.github.bedwarsrel.events.BedwarsPlayerLeaveEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Listen on game leave
 */
public class BedwarsPlayerLeaveListener implements Listener {

    @EventHandler
    public void onBedwarsPlayerLeave(BedwarsPlayerLeaveEvent event) {
        /*
         * Remove vote in ResourceVoting
         */
        if(BedwarsRelCloudNetAddon.getInstance().getPluginConfig().isResourceVotingEnabled()) BedwarsRelCloudNetAddon.getInstance().getResourceVotingManager().manageLeave(event.getPlayer().getUniqueId());
        /*
         * Remove BedwarsPlayer from storage
         */
        BedwarsRelCloudNetAddon.getInstance().getBedwarsRelPlayerManager().removeBedwarsRelPlayer(event.getPlayer().getUniqueId());
    }
}