package de.fridious.bedwarsrel.cloudnet.addon.listener;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 08.12.18 13:41
 *
 */

import de.fridious.bedwarsrel.cloudnet.addon.BedwarsRelCloudNetAddon;
import de.fridious.bedwarsrel.cloudnet.addon.resourcevoting.ResourceVotingManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

/**
 * Listen on inventory click
 */
public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        /*
         * Return if item is null or has material air
         */
        if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
        /*
         * Check if click entity is instance of player and clicked slot type is container
         */
        if(event.getWhoClicked() instanceof Player && event.getSlotType() == InventoryType.SlotType.CONTAINER) {
            final Player player = (Player) event.getWhoClicked();
            ResourceVotingManager resourceVotingManager = BedwarsRelCloudNetAddon.getInstance().getResourceVotingManager();
            /*
             * Check if clicked slot is slot of enable vote item
             */
            if(event.getSlot() == resourceVotingManager.getEnableVoteSlot())
                resourceVotingManager.vote(player.getUniqueId(), true);
            /*
             * Check if clicked slot is slot of disable vote item
             */
            else if(event.getSlot() == resourceVotingManager.getDisableVoteSlot())
                resourceVotingManager.vote(player.getUniqueId(), false);
        }
    }
}