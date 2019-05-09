package de.fridious.bedwarsrel.cloudnet.addon;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 25.11.18 13:36
 *
 */

import de.fridious.bedwarsrel.cloudnet.addon.cloudnet.CloudAPI;
import de.fridious.bedwarsrel.cloudnet.addon.cloudnet.CloudNetV2API;
import de.fridious.bedwarsrel.cloudnet.addon.cloudnet.CloudNetV3API;
import de.fridious.bedwarsrel.cloudnet.addon.commands.BedwarsRelCloudNetAddonCommand;
import de.fridious.bedwarsrel.cloudnet.addon.commands.ForceResourceCommand;
import de.fridious.bedwarsrel.cloudnet.addon.commands.StatsCommand;
import de.fridious.bedwarsrel.cloudnet.addon.config.Config;
import de.fridious.bedwarsrel.cloudnet.addon.listener.*;
import de.fridious.bedwarsrel.cloudnet.addon.player.BedwarsRelPlayerManager;
import de.fridious.bedwarsrel.cloudnet.addon.resourcevoting.ResourceVotingManager;
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
     * The BedwarsRelPlayerManager to manage all online BedwarsPlayer
     */
    private BedwarsRelPlayerManager bedwarsRelPlayerManager;

    /**
     * ResourceVotingManager to manage votes for voting
     */
    private ResourceVotingManager resourceVotingManager;

    /**
     * CloudAPI interface to implement all cloud versions
     */
    private CloudAPI cloudAPI;

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
         * Load and set the project version from the project properties
         */
        final Properties properties = new Properties();
        try {
            properties.load(this.getClassLoader().getResourceAsStream("project.properties"));
        } catch (IOException exception) {
            System.out.println(getPluginConfig().getConsolePrefix() + "Could't load version");
        }
        this.version = properties.getProperty("version");

        /*
         * Create a new instance of config and loading the config
         */
        this.config = new Config();
        this.config.loadConfig();

        /*
         * Create a new instance of BedwarsRelPlayerManager
         */
        this.bedwarsRelPlayerManager = new BedwarsRelPlayerManager();
    }

    /**
     * Called, when the plugin is enabled
     */
    @Override
    public void onEnable() {
        /*
         * Check if cloudnet is installed, if installed enable cloud features of this plugin.
         */
        if(Bukkit.getPluginManager().isPluginEnabled("CloudNetAPI")) {
            this.cloudAPI = new CloudNetV2API();
            System.out.println(getPluginConfig().getConsolePrefix() + "CloudNetV2 found");
        }
        else if(Bukkit.getPluginManager().isPluginEnabled("cloudnet-bridge")) {
            this.cloudAPI = new CloudNetV3API();
            System.out.println(getPluginConfig().getConsolePrefix() + "CloudNetV3 found");
        }
        else {
            this.cloudAPI = new CloudAPI() {
                @Override
                public void changeToIngame() {}
                @Override
                public void setMaxPlayers(int maxPlayers) {}
                @Override
                public void setMotd(String motd) {}
                @Override
                public void update() {}};
            System.out.println(getPluginConfig().getConsolePrefix() + "CloudNet wasn't found");
        }
        /*
         * Register commands
         */
        this.getCommand("bedwarsrelcloudnetaddon").setExecutor(new BedwarsRelCloudNetAddonCommand());
        this.getCommand("stats").setExecutor(new StatsCommand());
        /*
         * Register listeners
         */
        Bukkit.getPluginManager().registerEvents(new BedwarsGameStartedListener(), this);
        Bukkit.getPluginManager().registerEvents(new BedwarsPlayerJoinedListener(), this);
        Bukkit.getPluginManager().registerEvents(new BedwarsPlayerLeaveListener(), this);
        /*
         * Loop through all bedwars games and change following information's
         */
        BedwarsRel.getInstance().getGameManager().getGames().forEach(game -> {
            /*
             * Set the max players of the server to the amount of the bedwars game
             */
            cloudAPI.setMaxPlayers(game.getMaxPlayers());
            /*
             * Set the motd to the map name
             */
            cloudAPI.setMotd(game.getName());
        });
        /*
         * Update the information to cloud
         */
        cloudAPI.update();
        /*
         * Setup vault
         */
        setupVault();

        /*
         * Create a new instance of ResourceVotingManager, if resource voting is enabled and register listener and command for ResourceVoting
         */
        if(this.config.isResourceVotingEnabled()) {
            Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
            Bukkit.getPluginManager().registerEvents(new BedwarsResourceSpawnListener(), this);
            Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
            getCommand("forceresource").setExecutor(new ForceResourceCommand());
        }

        System.out.println(getPluginConfig().getConsolePrefix() + "BedwarsRelCloudNetAddon " + this.version + " is starting...");
        System.out.println(getPluginConfig().getConsolePrefix() + "Plugin is developed by Philipp Elvin Friedhoff/Fridious");
        System.out.println(getPluginConfig().getConsolePrefix() + "GitHub: https://github.com/fridious");
    }

    /**
     * Called, when the plugin is disabled
     */
    @Override
    public void onDisable() {
        System.out.println(getPluginConfig().getConsolePrefix() + "BedwarsRelCloudNetAddon v" + this.version + " is stopping...");
        System.out.println(getPluginConfig().getConsolePrefix() + "Plugin is developed by Philipp Elvin Friedhoff/Fridious");
        System.out.println(getPluginConfig().getConsolePrefix() + "GitHub: https://github.com/fridious");
    }

    /**
     * Setup method for vault integration
     */
    private void setupVault() {
        if(getServer().getPluginManager().getPlugin("Vault") == null)return;
        RegisteredServiceProvider<Economy> serviceProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if(serviceProvider == null)return;
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

    public BedwarsRelPlayerManager getBedwarsRelPlayerManager() {
        return bedwarsRelPlayerManager;
    }

    /**
     * Create a new instance of ResourceVotingManager, if not exist
     * @return ResourceVotingManager
     */
    public ResourceVotingManager getResourceVotingManager() {
        if(this.resourceVotingManager == null) this.resourceVotingManager = new ResourceVotingManager();
        return resourceVotingManager;
    }

    public CloudAPI getCloudAPI() {
        return cloudAPI;
    }

    public static BedwarsRelCloudNetAddon getInstance() {
        return instance;
    }
}