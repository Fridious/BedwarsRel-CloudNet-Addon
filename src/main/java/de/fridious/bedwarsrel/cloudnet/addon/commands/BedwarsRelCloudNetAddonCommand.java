package de.fridious.bedwarsrel.cloudnet.addon.commands;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 25.11.18 19:50
 *
 */

import de.fridious.bedwarsrel.cloudnet.addon.BedwarsRelCloudNetAddon;
import de.fridious.bedwarsrel.cloudnet.addon.config.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * An advertising command for Fridious
 */
public class BedwarsRelCloudNetAddonCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        /*
         * Send the advertising message to the console sender
         */
        Config config = BedwarsRelCloudNetAddon.getInstance().getPluginConfig();
        sender.sendMessage((sender instanceof Player ? config.getChatPrefix() : config.getConsolePrefix()) + "This plugin was developed by Fridious(GitHub: https://github.com/fridious)");
        return true;
    }
}