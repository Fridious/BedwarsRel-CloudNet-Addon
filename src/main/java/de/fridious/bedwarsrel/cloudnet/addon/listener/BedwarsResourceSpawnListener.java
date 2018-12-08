package de.fridious.bedwarsrel.cloudnet.addon.listener;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 07.12.18 22:47
 *
 */

import de.fridious.bedwarsrel.cloudnet.addon.BedwarsRelCloudNetAddon;
import io.github.bedwarsrel.events.BedwarsResourceSpawnEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Bed;

/**
 * Listen on resource spawning
 */
public class BedwarsResourceSpawnListener implements Listener {

    @EventHandler
    public void onBedwarsResourceSpawn(BedwarsResourceSpawnEvent event) {
        ItemStack spawnedResource = event.getResource();
        ItemStack disabledResource = BedwarsRelCloudNetAddon.getInstance().getResourceVotingManager().getVotingType();
        /*
         * Check if resource spawning is disabled, if spawned resource equals disabled resource
         */
        if(!BedwarsRelCloudNetAddon.getInstance().getResourceVotingManager().isResourceEnabled()
                && spawnedResource.getType() == disabledResource.getType()
                && spawnedResource.getData().getData() == disabledResource.getData().getData()) {
            /*
             * Cancel spawning of resource
             */
            event.setCancelled(true);
        }
    }
}