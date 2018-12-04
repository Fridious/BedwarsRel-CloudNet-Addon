package de.fridious.bedwarsrel.cloudnet.addon.listener;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 04.12.18 14:41
 *
 */

import de.fridious.bedwarsrel.cloudnet.addon.BedwarsRelCloudNetAddon;
import de.fridious.bedwarsrel.cloudnet.addon.config.Config;
import de.fridious.bedwarsrel.cloudnet.addon.events.BedwarsPlayerFinalKilledEvent;
import io.github.bedwarsrel.events.BedwarsPlayerKilledEvent;
import io.github.bedwarsrel.game.Team;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Listen on player kill
 */
public class BedwarsPlayerKilledListener implements Listener {

    @EventHandler
    public void onBedwarsPlayerKilled(BedwarsPlayerKilledEvent event) {
        /*
         * Add reward for the killer
         */
        Team team = event.getGame().getPlayerTeam(event.getPlayer());
        if(team.getHeadTarget().getType() != Material.BED_BLOCK && team.getFeetTarget().getType() != Material.BED_BLOCK)
            Bukkit.getPluginManager().callEvent(new BedwarsPlayerFinalKilledEvent(event.getGame(), event.getPlayer(), event.getKiller()));
        else if(event.getKiller() != null)
            BedwarsRelCloudNetAddon.getInstance().getEconomy().depositPlayer(event.getKiller(), BedwarsRelCloudNetAddon.getInstance().getPluginConfig().getKillReward());
    }
}