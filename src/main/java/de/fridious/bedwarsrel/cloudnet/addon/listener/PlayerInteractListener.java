package de.fridious.bedwarsrel.cloudnet.addon.listener;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 08.12.18 12:37
 *
 */

import de.fridious.bedwarsrel.cloudnet.addon.BedwarsRelCloudNetAddon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Listen on interact
 */
public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getItem() == null || event.getItem().getType() == null) return;
        /*
         * Check if item is hotbar item to open voting inventory
         */
        if(event.getItem().equals(BedwarsRelCloudNetAddon.getInstance().getResourceVotingManager().getHotbarItem()))
            event.getPlayer().openInventory(BedwarsRelCloudNetAddon.getInstance().getResourceVotingManager().getVotingInventory());
    }
}