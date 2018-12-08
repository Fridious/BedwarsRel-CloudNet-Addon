package de.fridious.bedwarsrel.cloudnet.addon.commands;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 08.12.18 18:08
 *
 */

import de.fridious.bedwarsrel.cloudnet.addon.BedwarsRelCloudNetAddon;
import de.fridious.bedwarsrel.cloudnet.addon.config.Config;
import de.fridious.bedwarsrel.cloudnet.addon.resourcevoting.ResourceVotingManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * ForceResourceCommand to force resource spawning
 */
public class ForceResourceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Config config = BedwarsRelCloudNetAddon.getInstance().getPluginConfig();
        /*
         * Check if sender is instance of player
         */
        if(sender instanceof Player) {
            final Player player = (Player)sender;
            /*
             * Check if player has permission to execute this command
             */
            if(player.hasPermission(config.getForceResourcePermission())) {
                ResourceVotingManager resourceVotingManager = BedwarsRelCloudNetAddon.getInstance().getResourceVotingManager();
                /*
                 * Check if command length is correct
                 */
                if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("true")) {
                        /*
                         * Set ForceVoting to true
                         */
                        resourceVotingManager.setForceVoting(true);
                        resourceVotingManager.setForceVotingResult(true);
                        player.sendMessage(config.getForceResourceEnabled());
                    } else if(args[0].equalsIgnoreCase("false")) {
                        /*
                         * Set ForceVoting to false
                         */
                        resourceVotingManager.setForceVoting(true);
                        resourceVotingManager.setForceVotingResult(false);
                        player.sendMessage(config.getForceResourceDisabled());
                    } else {
                        player.sendMessage(config.getForceResourceHelp().replace("[command]", label));
                    }
                } else {
                    player.sendMessage(config.getForceResourceHelp().replace("[command]", label));
                }
            } else {
                player.sendMessage(config.getNoPermission());
            }
        } else {
            sender.sendMessage(config.getNotFromConsole());
        }
        return true;
    }
}