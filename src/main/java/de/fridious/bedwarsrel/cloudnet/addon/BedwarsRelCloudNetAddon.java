package de.fridious.bedwarsrel.cloudnet.addon;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 25.11.18 13:36
 *
 */

import de.dytanic.cloudnet.bridge.CloudServer;
import de.fridious.bedwarsrel.cloudnet.addon.commands.BedwarsRelCloudNetAddonCommand;
import de.fridious.bedwarsrel.cloudnet.addon.listener.BedwarsGameStartedListener;
import io.github.bedwarsrel.BedwarsRel;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BedwarsRelCloudNetAddon extends JavaPlugin {

    /*
    The field to save the instance of this class
     */
    private static BedwarsRelCloudNetAddon instance;

    /*
    The fields for the console prefix, chat prefix and version
     */
    private String consolePrefix, chatPrefix, version;

    /*
    Called, when the plugin is loaded
     */
    @Override
    public void onLoad() {
        /*
        Set the instance of this plugin
         */
        instance = this;
        /*
        Set the prefixes
         */
        this.consolePrefix = "[BedwarsRelCloudNetAddon] ";
        this.chatPrefix = "§8[§4BedwarsRelCloudNetAddonCommand§8] §7";
        this.version = "1.0";
    }

    /*
    Called, when the plugin is enabled
     */
    @Override
    public void onEnable() {
        /*
        Register the advertisement command
         */
        this.getCommand("bedwarsrelcloudnetaddon").setExecutor(new BedwarsRelCloudNetAddonCommand());
        /*
        Register the BedwarsGameStartedListener
         */
        Bukkit.getPluginManager().registerEvents(new BedwarsGameStartedListener(), this);
        /*
        Loop through all bedwars games and change following information's
         */
        BedwarsRel.getInstance().getGameManager().getGames().forEach(game -> {
            /*
            Set the max players of the server to the amount of the bedwars game
             */
            CloudServer.getInstance().setMaxPlayers(game.getMaxPlayers());
            /*
            Set the motd to the map name
             */
            CloudServer.getInstance().setMotd(game.getName());
            /*
            Update the informations async to the cloud
             */
            CloudServer.getInstance().updateAsync();
        });
        System.out.println(getConsolePrefix() + "BedwarsRelCloudNetAddon " + this.version + " starting...");
        System.out.println(getConsolePrefix() + "Plugin is developed by Fridious");
        System.out.println(getConsolePrefix() + "GitHub: https://github.com/fridious");
    }

    /*
    Called, when the plugin is disabled
     */
    @Override
    public void onDisable() {
        System.out.println(getConsolePrefix() + "BedwarsRelCloudNetAddon v" + this.version + " stopping...");
        System.out.println(getConsolePrefix() + "Plugin is developed by Fridious");
        System.out.println(getConsolePrefix() + "GitHub: https://github.com/fridious");
    }

    /*
    Get the console prefix
     */
    public String getConsolePrefix() {
        return consolePrefix;
    }

    /*
    Get the chat prefix
     */
    public String getChatPrefix() {
        return chatPrefix;
    }

    /*
    Get the instance of this class
     */
    public static BedwarsRelCloudNetAddon getInstance() {
        return instance;
    }
}