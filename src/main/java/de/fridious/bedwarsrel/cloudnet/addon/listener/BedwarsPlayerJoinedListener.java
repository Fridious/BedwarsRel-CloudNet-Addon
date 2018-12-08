package de.fridious.bedwarsrel.cloudnet.addon.listener;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 08.12.18 12:44
 *
 */

import de.fridious.bedwarsrel.cloudnet.addon.BedwarsRelCloudNetAddon;
import de.fridious.bedwarsrel.cloudnet.addon.resourcevoting.ResourceVotingManager;
import io.github.bedwarsrel.events.BedwarsPlayerJoinedEvent;
import io.github.bedwarsrel.game.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

/**
 * Listen on game join
 */
public class BedwarsPlayerJoinedListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(BedwarsPlayerJoinedEvent event) {
        /*
         * Load BedwarsRel Player into storage
         */
        BedwarsRelCloudNetAddon.getInstance().getBedwarsRelPlayerManager().createBedwarsRelPlayer(event.getPlayer().getUniqueId());
        /*
         * Add resource voting item to inventory, if game isn't running
         */
        if(event.getGame().getState() != GameState.RUNNING && BedwarsRelCloudNetAddon.getInstance().getPluginConfig().isResourceVotingEnabled()) {
            ResourceVotingManager resourceVotingManager = BedwarsRelCloudNetAddon.getInstance().getResourceVotingManager();
            event.getPlayer().getInventory().setItem(resourceVotingManager.getHotbarItemSlot(), resourceVotingManager.getHotbarItem().clone());
        }
    }
}