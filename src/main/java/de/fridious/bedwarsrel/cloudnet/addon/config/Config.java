package de.fridious.bedwarsrel.cloudnet.addon.config;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 04.12.18 14:51
 *
 */

import de.fridious.bedwarsrel.cloudnet.addon.BedwarsRelCloudNetAddon;
import org.bukkit.Bukkit;

import java.io.File;

public class Config extends SimpleConfig {

    /*
     * Fields for messages
     */
    private String chatPrefix, consolePrefix;
    /*
     * Fields for game settings
     */
    private int spectatorSlots;
    private boolean killRewardEnabled, finalKillRewardEnabled, winRewardEnabled, targetBlockDestroyedRewardEnabled, targetBlockDestroyedTeamReward;
    private double killReward, finalKillReward, winReward, targetBlockDestroyedReward;


    public Config() {
        super(new File(BedwarsRelCloudNetAddon.getInstance().getDataFolder(), "config.yml"));
        this.chatPrefix = "&8[&4BedwarsRelCloudNetAddon&8] &7";
        this.consolePrefix = "[BedwarsRelCloudNetAddon] ";
        this.spectatorSlots = 100;
        this.killRewardEnabled = true;
        this.finalKillRewardEnabled = true;
        this.winRewardEnabled = true;
        this.targetBlockDestroyedRewardEnabled = true;
        this.targetBlockDestroyedTeamReward = true;
        this.killReward = 5;
        this.finalKillReward = 30;
        this.winReward = 100;
        this.targetBlockDestroyedReward = 50;
    }

    @Override
    protected void onLoad() {
        /*
         * Set messages and settings
         */
        this.chatPrefix = getMessageValue("messages.prefix.chat");
        this.consolePrefix = getMessageValue("messages.prefix.console");
        this.spectatorSlots = getIntValue("settings.spectatorslots");
        this.killRewardEnabled = getBooleanValue("rewards.kill.enabled");
        this.killReward = getDoubleValue("rewards.kill.reward");
        this.finalKillRewardEnabled = getBooleanValue("rewards.finalkill.enabled");
        this.finalKillReward = getDoubleValue("rewards.finalkill.reward");
        this.winRewardEnabled = getBooleanValue("rewards.win.enabled");
        this.winReward = getDoubleValue("rewards.win.reward");
        this.targetBlockDestroyedRewardEnabled = getBooleanValue("rewards.targetblockdestroyed.enabled");
        this.targetBlockDestroyedTeamReward = getBooleanValue("rewards.targetblockdestroyed.teamreward");
        this.targetBlockDestroyedReward = getDoubleValue("rewards.targetblockdestroyed.reward");
    }

    @Override
    protected void registerDefaults() {
        /*
         * Register config defaults
         */
        addValue("messages.prefix.chat", this.chatPrefix);
        addValue("messages.prefix.console", this.consolePrefix);
        addValue("settings.spectatorslots", this.spectatorSlots);
        addValue("rewards.finalkill.enabled", this.finalKillRewardEnabled);
        addValue("rewards.finalkill.reward", this.finalKillReward);
        addValue("rewards.kill.enabled", this.killRewardEnabled);
        addValue("rewards.kill.reward", this.killReward);
        addValue("rewards.win.enabled", this.winRewardEnabled);
        addValue("rewards.win.reward", this.winReward);
        addValue("rewards.targetblockdestroyed.enabled", this.targetBlockDestroyedRewardEnabled);
        addValue("rewards.targetblockdestroyed.teamreward", this.targetBlockDestroyedTeamReward);
        addValue("rewards.targetblockdestroyed.reward", this.targetBlockDestroyedReward);
    }

    @Override
    protected void onFailed() {
        Bukkit.getPluginManager().disablePlugin(BedwarsRelCloudNetAddon.getInstance());
    }

    public String getChatPrefix() {
        return chatPrefix;
    }

    public String getConsolePrefix() {
        return consolePrefix;
    }

    public int getSpectatorSlots() {
        return spectatorSlots;
    }

    public boolean isKillRewardEnabled() {
        return killRewardEnabled;
    }

    public boolean isFinalKillRewardEnabled() {
        return finalKillRewardEnabled;
    }

    public boolean isWinRewardEnabled() {
        return winRewardEnabled;
    }

    public boolean isTargetBlockDestroyedRewardEnabled() {
        return targetBlockDestroyedRewardEnabled;
    }

    public boolean isTargetBlockDestroyedTeamReward() {
        return targetBlockDestroyedTeamReward;
    }

    public double getKillReward() {
        return killReward;
    }

    public double getFinalKillReward() {
        return finalKillReward;
    }

    public double getWinReward() {
        return winReward;
    }

    public double getTargetBlockDestroyedReward() {
        return targetBlockDestroyedReward;
    }
}