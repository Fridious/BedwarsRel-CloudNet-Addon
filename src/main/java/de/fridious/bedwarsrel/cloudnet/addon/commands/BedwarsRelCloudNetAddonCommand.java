package de.fridious.bedwarsrel.cloudnet.addon.commands;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 25.11.18 19:50
 *
 */

import de.fridious.bedwarsrel.cloudnet.addon.BedwarsRelCloudNetAddon;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BedwarsRelCloudNetAddonCommand implements CommandExecutor {

    /*
    An advertising command for Fridious
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        /*
        Send the advertising message to the console sender
         */
        sender.sendMessage((sender instanceof Player ? BedwarsRelCloudNetAddon.getInstance().getChatPrefix() : BedwarsRelCloudNetAddon.getInstance().getConsolePrefix()) + "This plugin was developed by Fridious(GitHub: https://github.com/fridious)");
        return true;
    }
}