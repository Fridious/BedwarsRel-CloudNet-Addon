package de.fridious.bedwarsrel.cloudnet.addon.config;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 04.12.18 14:51
 *
 */

import de.fridious.bedwarsrel.cloudnet.addon.BedwarsRelCloudNetAddon;
import de.fridious.bedwarsrel.cloudnet.addon.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Arrays;

public class Config extends SimpleConfig {

    /*
     * Fields for messages
     */
    private String chatPrefix, consolePrefix, resourceVotingEnabledResult, resourceVotingDisabledResult, notFromConsole
            , noPermission, forceResourceHelp, forceResourceEnabled, forceResourceDisabled, forceResourcePermission;
    /*
     * Fields for game settings
     */
    private int spectatorSlots;
    private boolean killRewardEnabled, finalKillRewardEnabled, winRewardEnabled, targetBlockDestroyedRewardEnabled,
            targetBlockDestroyedTeamReward, resourceVotingEnabled, broadcastResourceVotingResult;
    private double killReward, finalKillReward, winReward, targetBlockDestroyedReward;


    public Config() {
        super(new File(BedwarsRelCloudNetAddon.getInstance().getDataFolder(), "config.yml"));
        this.chatPrefix = "&8[&4BedwarsRelCloudNetAddon&8] &7";
        this.consolePrefix = "[BedwarsRelCloudNetAddon] ";
        this.resourceVotingEnabledResult = "[prefix]&6Gold is &aenabled";
        this.resourceVotingDisabledResult = "[prefix]&6Gold is &cdisabled";
        this.notFromConsole = "[prefix]You can't execute this command from console.";
        this.noPermission = "[prefix]You don't have enough permission to execute [command]";
        this.forceResourceHelp = "[prefix]Use /forceresource <true, false>";
        this.forceResourceEnabled = "[prefix]You have forced resource spawning.";
        this.forceResourceDisabled = "[prefix]You have unforced resource spawning.";
        this.forceResourcePermission = "bedwarsrelcloudnetaddon.forceresource";
        this.spectatorSlots = 100;
        this.killRewardEnabled = true;
        this.finalKillRewardEnabled = true;
        this.winRewardEnabled = true;
        this.targetBlockDestroyedRewardEnabled = true;
        this.targetBlockDestroyedTeamReward = true;
        this.resourceVotingEnabled = true;
        this.broadcastResourceVotingResult = true;
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
        this.notFromConsole = getMessageValue("messages.notfromconsole").replace("[prefix]", this.consolePrefix);
        this.noPermission = getMessageValue("messages.nopermission").replace("[prefix]", this.chatPrefix);
        this.resourceVotingEnabledResult = getMessageValue("messages.resourcevoting.enabled").replace("[prefix]", this.chatPrefix);
        this.resourceVotingDisabledResult = getMessageValue("messages.resourcevoting.disabled").replace("[prefix]", this.chatPrefix);
        this.forceResourceHelp = getMessageValue("messages.resourcevoting.forceresource.help").replace("[prefix]", this.chatPrefix);
        this.forceResourceEnabled = getMessageValue("messages.resourcevoting.forceresource.enabled").replace("[prefix]", this.chatPrefix);
        this.forceResourceDisabled = getMessageValue("messages.resourcevoting.forceresource.disabled").replace("[prefix]", this.chatPrefix);
        this.forceResourcePermission = getStringValue("command.forceresource.permission");
        this.broadcastResourceVotingResult = getBooleanValue("resourcevoting.broadcastresult");
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
        this.resourceVotingEnabled = getBooleanValue("resourcevoting.enabled");
        if(isResourceVotingEnabled()) {
            BedwarsRelCloudNetAddon.getInstance().getResourceVotingManager().setVotingType(new ItemBuilder(getMessageValue("resourcevoting.resourcetype")).build());
            if(contains("resourcevoting.hotbaritem.slot")) BedwarsRelCloudNetAddon.getInstance().getResourceVotingManager().setHotbarItemSlot(getIntValue("resourcevoting.hotbaritem.slot"));
            if(contains("resourcevoting.hotbaritem.type")) {
                ItemBuilder itemBuilder = new ItemBuilder(getMessageValue("resourcevoting.hotbaritem.type"));
                if(contains("resourcevoting.hotbaritem.displayname")) itemBuilder.setDisplayName(getMessageValue("resourcevoting.hotbaritem.displayname"));
                if(contains("resourcevoting.hotbaritem.lore")) itemBuilder.setLore(getMessageListValue("resourcevoting.hotbaritem.lore"));
                BedwarsRelCloudNetAddon.getInstance().getResourceVotingManager().setHotbarItem(itemBuilder.build());
            }
            if(contains("resourcevoting.inventory")) {
                Inventory inventory;
                int size = getIntValue("resourcevoting.inventory.size");
                if(size == 0) size = 27;
                if(contains("resourcevoting.inventory.title")) inventory = Bukkit.createInventory(null, size, getMessageValue("resourcevoting.inventory.title"));
                else inventory = Bukkit.createInventory(null, size);
                getKeys("resourcevoting.inventory.items").forEach((path)-> {
                    path = "resourcevoting.inventory.items."+path;
                    int slot = getIntValue(path+".slot");

                    ItemBuilder itemBuilder = new ItemBuilder(getMessageValue(path+".type"));
                    if(contains(path+".displayname")) itemBuilder.setDisplayName(getMessageValue(path+".displayname"));
                    if(contains(path+".lore")) itemBuilder.setLore(getMessageListValue(path+".lore"));
                    ItemStack itemStack = itemBuilder.build();
                    inventory.setItem(slot, itemStack);

                    if(path.contains("enableitem")) {
                        BedwarsRelCloudNetAddon.getInstance().getResourceVotingManager().setEnableVoteSlot(slot);
                        BedwarsRelCloudNetAddon.getInstance().getResourceVotingManager().setEnableVoteItem(itemStack.clone());
                    } else if(path.contains("disableitem")) {
                        BedwarsRelCloudNetAddon.getInstance().getResourceVotingManager().setDisableVoteSlot(slot);
                        BedwarsRelCloudNetAddon.getInstance().getResourceVotingManager().setDisableVoteItem(itemStack.clone());
                    }
                });

                BedwarsRelCloudNetAddon.getInstance().getResourceVotingManager().setVotingInventory(inventory);
            }
        }


    }

    @Override
    protected void registerDefaults() {
        /*
         * Register config defaults
         */
        addValue("messages.prefix.chat", this.chatPrefix);
        addValue("messages.prefix.console", this.consolePrefix);
        addValue("messages.notfromconsole", this.notFromConsole);
        addValue("messages.nopermission", this.noPermission);
        addValue("messages.resourcevoting.enabled", this.resourceVotingEnabledResult);
        addValue("messages.resourcevoting.disabled", this.resourceVotingDisabledResult);

        addValue("messages.resourcevoting.forceresource.help", this.forceResourceHelp);
        addValue("messages.resourcevoting.forceresource.enabled", this.forceResourceEnabled);
        addValue("messages.resourcevoting.forceresource.disabled", this.forceResourceDisabled);

        addValue("command.forceresource.permission", this.forceResourcePermission);

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

        addValue("resourcevoting.enabled", this.resourceVotingEnabled);
        addValue("resourcevoting.broadcastresult", this.broadcastResourceVotingResult);
        addValue("resourcevoting.resourcetype", "266:0");

        addValue("resourcevoting.hotbaritem.slot", 6);
        addValue("resourcevoting.hotbaritem.displayname", "&6Gold voting");
        addValue("resourcevoting.hotbaritem.type", "266:0");
        addValue("resourcevoting.hotbaritem.lore", Arrays.asList("Vote for enable or disable gold"));

        addValue("resourcevoting.inventory.title", "&6Gold voting");
        addValue("resourcevoting.inventory.size", 27);

        addValue("resourcevoting.inventory.items.enableitem.slot", 11);
        addValue("resourcevoting.inventory.items.enableitem.type", "351:10");
        addValue("resourcevoting.inventory.items.enableitem.displayname", "&aEnable");
        addValue("resourcevoting.inventory.items.enableitem.lore", Arrays.asList("&aEnable gold", "&e[enable_votes]"));

        addValue("resourcevoting.inventory.items.disableitem.slot", 15);
        addValue("resourcevoting.inventory.items.disableitem.type", "351:8");
        addValue("resourcevoting.inventory.items.disableitem.displayname", "&7Disable");
        addValue("resourcevoting.inventory.items.disableitem.lore", Arrays.asList("&cDisable gold", "&e[disable_votes]"));
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

    public boolean isResourceVotingEnabled() {
        return resourceVotingEnabled;
    }

    public String getResourceVotingEnabledResult() {
        return resourceVotingEnabledResult;
    }

    public String getResourceVotingDisabledResult() {
        return resourceVotingDisabledResult;
    }

    public boolean isBroadcastResourceVotingResult() {
        return broadcastResourceVotingResult;
    }

    public String getNotFromConsole() {
        return notFromConsole;
    }

    public String getNoPermission() {
        return noPermission;
    }

    public String getForceResourceHelp() {
        return forceResourceHelp;
    }

    public String getForceResourceEnabled() {
        return forceResourceEnabled;
    }

    public String getForceResourceDisabled() {
        return forceResourceDisabled;
    }

    public String getForceResourcePermission() {
        return forceResourcePermission;
    }
}