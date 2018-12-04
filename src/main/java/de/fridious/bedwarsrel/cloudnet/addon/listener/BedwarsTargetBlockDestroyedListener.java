package de.fridious.bedwarsrel.cloudnet.addon.listener;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 04.12.18 14:43
 *
 */

import de.fridious.bedwarsrel.cloudnet.addon.BedwarsRelCloudNetAddon;
import de.fridious.bedwarsrel.cloudnet.addon.config.Config;
import io.github.bedwarsrel.events.BedwarsTargetBlockDestroyedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Listen on target block destroy
 */
public class BedwarsTargetBlockDestroyedListener implements Listener {

    @EventHandler
    public void onBedwarsTargetBlockDestroyed(BedwarsTargetBlockDestroyedEvent event) {
        Config config = BedwarsRelCloudNetAddon.getInstance().getPluginConfig();
        if(config.isTargetBlockDestroyedTeamReward() && event.getTeam() != null) {
            /*
             * Add reward for all players in team
             */
            event.getGame().getPlayerTeam(event.getPlayer()).getPlayers().forEach((player) ->
                    BedwarsRelCloudNetAddon.getInstance().getEconomy().depositPlayer(player, config.getTargetBlockDestroyedReward()));
        } else if(event.getPlayer() != null) {
            /*
             * Add reward for the bed destroyer
             */
            BedwarsRelCloudNetAddon.getInstance().getEconomy().depositPlayer(event.getPlayer(), config.getTargetBlockDestroyedReward());
        }
    }
}