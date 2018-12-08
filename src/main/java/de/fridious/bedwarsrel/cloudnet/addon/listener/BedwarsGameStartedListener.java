package de.fridious.bedwarsrel.cloudnet.addon.listener;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 25.11.18 13:53
 *
 */

import de.fridious.bedwarsrel.cloudnet.addon.BedwarsRelCloudNetAddon;
import de.fridious.bedwarsrel.cloudnet.addon.config.Config;
import de.fridious.bedwarsrel.cloudnet.addon.resourcevoting.ResourceVotingManager;
import io.github.bedwarsrel.events.BedwarsGameStartedEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Listen on game start
 */
public class BedwarsGameStartedListener implements Listener {

    @EventHandler
    public void onBedwarsGameStart(BedwarsGameStartedEvent event) {
        /*
         * Change the game to ingame in CloudNet
         */
        BedwarsRelCloudNetAddon.getInstance().getCloudAPI().changeToIngame();
        /*
         * Set the max players of the server to max players + spectator slots in config
         */
        BedwarsRelCloudNetAddon.getInstance().getCloudAPI().setMaxPlayers(event.getGame().getMaxPlayers() + BedwarsRelCloudNetAddon.getInstance().getPluginConfig().getSpectatorSlots());
        /*
         * Update information to cloud
         */
        BedwarsRelCloudNetAddon.getInstance().getCloudAPI().update();
        /*
         * Send message to all players, if resource voting is enabled and broadcast message is enabled
         */
        ResourceVotingManager resourceVotingManager = BedwarsRelCloudNetAddon.getInstance().getResourceVotingManager();
        Config config = BedwarsRelCloudNetAddon.getInstance().getPluginConfig();
        if(config.isResourceVotingEnabled() && config.isBroadcastResourceVotingResult()) {
            Bukkit.getOnlinePlayers().forEach((player)-> {
                if(resourceVotingManager.isResourceEnabled()) player.sendMessage(config.getResourceVotingEnabledResult());
                else player.sendMessage(config.getResourceVotingDisabledResult());
            });
        }

    }
}