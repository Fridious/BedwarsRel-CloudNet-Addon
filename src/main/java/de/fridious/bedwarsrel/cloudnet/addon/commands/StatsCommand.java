package de.fridious.bedwarsrel.cloudnet.addon.commands;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 04.12.18 18:56
 *
 */

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        StringBuilder builder = new StringBuilder();
        builder.append("bw stats");
        for(String string : args) builder.append(" ").append(string);
        if(sender instanceof Player) Bukkit.dispatchCommand(sender, builder.toString());
        return true;
    }
}