package de.fridious.bedwarsrel.cloudnet.addon;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 25.11.18 13:36
 *
 */

import de.dytanic.cloudnet.bridge.CloudServer;
import de.fridious.bedwarsrel.cloudnet.addon.commands.BedwarsRelCloudNetAddonCommand;
import de.fridious.bedwarsrel.cloudnet.addon.config.Config;
import de.fridious.bedwarsrel.cloudnet.addon.listener.*;
import io.github.bedwarsrel.BedwarsRel;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Properties;

/**
 * The core class of this addon
 */
public class BedwarsRelCloudNetAddon extends JavaPlugin {

    /**
     * The field to save the instance of this class
     */
    private static BedwarsRelCloudNetAddon instance;

    /**
     * The field for the plugin version
     */
    private String version;

    /**
     * The economy field for vault
     */
    private Economy economy;

    /**
     * The config field
     */
    private Config config;

    /**
     * Called, when the plugin is loaded
     */
    @Override
    public void onLoad() {
        /*
         * Set the instance of this plugin
         */
        instance = this;

        /*
         * Create a new instance of config and loading the config
         */
        this.config = new Config();
        this.config.loadConfig();

        /*
        Load and set the project version from the project properties
         */
        final Properties properties = new Properties();
        try {
            properties.load(this.getClassLoader().getResourceAsStream("project.properties"));
        } catch (IOException exception) {
            System.out.println(getPluginConfig().getConsolePrefix() + "Could't load version");
            exception.printStackTrace();
        }
        this.version = properties.getProperty("version");
    }

    /**
     * Called, when the plugin is enabled
     */
    @Override
    public void onEnable() {
        /*
         * Register the advertisement command
         */
        this.getCommand("bedwarsrelcloudnetaddon").setExecutor(new BedwarsRelCloudNetAddonCommand());
        /*
         * Register the BedwarsGameStartListener
         */
        Bukkit.getPluginManager().registerEvents(new BedwarsGameStartListener(), this);
        /*
         * Loop through all bedwars games and change following information's
         */
        BedwarsRel.getInstance().getGameManager().getGames().forEach(game -> {
            /*
             * Set the max players of the server to the amount of the bedwars game
             */
            CloudServer.getInstance().setMaxPlayers(game.getMaxPlayers());
            /*
             * Set the motd to the map name
             */
            CloudServer.getInstance().setMotd(game.getName());
            /*
             * Update the informations async to the cloud
             */
            CloudServer.getInstance().updateAsync();
        });
        /*
         * Setup vault
         */
        setupVault();

        System.out.println(getPluginConfig().getConsolePrefix() + "BedwarsRelCloudNetAddon " + this.version + " is starting...");
        System.out.println(getPluginConfig().getConsolePrefix() + "Plugin is developed by Fridious");
        System.out.println(getPluginConfig().getConsolePrefix() + "GitHub: https://github.com/fridious");
    }

    /**
     * Called, when the plugin is disabled
     */
    @Override
    public void onDisable() {
        System.out.println(getPluginConfig().getConsolePrefix() + "BedwarsRelCloudNetAddon v" + this.version + " is stopping...");
        System.out.println(getPluginConfig().getConsolePrefix() + "Plugin is developed by Fridious");
        System.out.println(getPluginConfig().getConsolePrefix() + "GitHub: https://github.com/fridious");
    }

    /**
     * Setup method for vault integration
     */
    private void setupVault() {
        if(getServer().getPluginManager().getPlugin("Vault") == null)return;
        RegisteredServiceProvider<Economy> serviceProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if(serviceProvider == null) return;
        this.economy = serviceProvider.getProvider();
        /*
         * Register bedwars listener for getting money in the game, if enabled
         */
        if(config.isWinRewardEnabled())Bukkit.getPluginManager().registerEvents(new BedwarsGameOverListener(), this);
        if(config.isKillRewardEnabled())Bukkit.getPluginManager().registerEvents(new BedwarsPlayerKilledListener(), this);
        if(config.isFinalKillRewardEnabled())Bukkit.getPluginManager().registerEvents(new BedwarsPlayerFinalKilledListener(), this);
        if(config.isTargetBlockDestroyedRewardEnabled())Bukkit.getPluginManager().registerEvents(new BedwarsTargetBlockDestroyedListener(), this);
    }

    public Economy getEconomy() {
        return economy;
    }

    public Config getPluginConfig() {
        return config;
    }

    public static BedwarsRelCloudNetAddon getInstance() {
        return instance;
    }
}