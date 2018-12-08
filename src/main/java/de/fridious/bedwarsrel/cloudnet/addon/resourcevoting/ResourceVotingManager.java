package de.fridious.bedwarsrel.cloudnet.addon.resourcevoting;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 07.12.18 22:56
 *
 */

import de.fridious.bedwarsrel.cloudnet.addon.BedwarsRelCloudNetAddon;
import de.fridious.bedwarsrel.cloudnet.addon.player.BedwarsRelPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class ResourceVotingManager {

    private ItemStack hotbarItem, votingType, enableVoteItem, disableVoteItem;
    private int enabledVotes, disabledVotes, enableVoteSlot, disableVoteSlot, hotbarItemSlot;
    private Inventory votingInventory;
    private boolean forceVoting, forceVotingResult;

    public ResourceVotingManager() {
        this.forceVoting = false;
        this.forceVotingResult = false;
    }

    public ItemStack getHotbarItem() {
        return hotbarItem;
    }

    public ItemStack getVotingType() {
        return votingType;
    }

    public Inventory getVotingInventory() {
        return votingInventory;
    }

    public int getEnableVoteSlot() {
        return enableVoteSlot;
    }

    public int getDisableVoteSlot() {
        return disableVoteSlot;
    }

    public ItemStack getEnableVoteItem() {
        return enableVoteItem;
    }

    public ItemStack getDisableVoteItem() {
        return disableVoteItem;
    }

    public int getHotbarItemSlot() {
        return hotbarItemSlot;
    }

    public boolean isForceVoting() {
        return forceVoting;
    }

    public boolean getForceVotingResult() {
        return forceVotingResult;
    }

    /**
     * Return resource spawning and check if forceVoting
     * @return boolean if resource spawning is enabled
     */
    public boolean isResourceEnabled() {
        if(isForceVoting()) return forceVotingResult;
        return this.enabledVotes >= this.disabledVotes;
    }

    public void setForceVoting(boolean forceVoting) {
        this.forceVoting = forceVoting;
    }

    public void setForceVotingResult(boolean forceVotingResult) {
        this.forceVotingResult = forceVotingResult;
    }

    public void setHotbarItem(ItemStack hotbarItem) {
        this.hotbarItem = hotbarItem;
    }

    public void setVotingType(ItemStack votingType) {
        this.votingType = votingType;
    }

    public void setVotingInventory(Inventory votingInventory) {
        this.votingInventory = votingInventory;
        setVotingInventoryContent();
    }

    /**
     * Update voting inventory content and replace variables
     */
    public void setVotingInventoryContent() {
        int i = 0;
        for(ItemStack itemStack : this.votingInventory.getContents()) {
            if(i == enableVoteSlot) itemStack = this.enableVoteItem.clone();
            else if(i == disableVoteSlot) itemStack = this.disableVoteItem.clone();
            if(itemStack != null && itemStack.getType() != Material.AIR) {
                if(itemStack.hasItemMeta()) {
                    ItemMeta itemMeta = itemStack.getItemMeta().clone();
                    if(itemStack.getItemMeta().hasLore()) {
                        List<String> replacedLore = new LinkedList<>();
                        for (String lore : itemStack.getItemMeta().getLore()) {
                            replacedLore.add(lore.replace("[enable_votes]", String.valueOf(this.enabledVotes))
                                    .replace("[disable_votes]", String.valueOf(this.disabledVotes)));
                        }
                        itemMeta.setLore(replacedLore);
                    }
                    if(itemStack.getItemMeta().hasDisplayName()) {
                        String displayName = itemStack.getItemMeta().getDisplayName()
                                .replace("[enable_votes]", String.valueOf(this.enabledVotes))
                                .replace("[disable_votes]", String.valueOf(this.disabledVotes));
                        itemMeta.setDisplayName(displayName);
                    }
                    itemStack.setItemMeta(itemMeta);
                }
            }
            this.votingInventory.setItem(i, itemStack);
            i++;
        }
    }

    public void setEnableVoteSlot(int enableVoteSlot) {
        this.enableVoteSlot = enableVoteSlot;
    }

    public void setDisableVoteSlot(int disableVoteSlot) {
        this.disableVoteSlot = disableVoteSlot;
    }

    public void setEnableVoteItem(ItemStack enableVoteItem) {
        this.enableVoteItem = enableVoteItem;
    }

    public void setDisableVoteItem(ItemStack disableVoteItem) {
        this.disableVoteItem = disableVoteItem;
    }

    public void setHotbarItemSlot(int hotbarItemSlot) {
        this.hotbarItemSlot = hotbarItemSlot;
    }

    /**
     * Manage vote and update gui, if not same vote
     * @param uuid of player
     * @param enable if vote is for enabling or disabling resource spawning
     */
    public void vote(UUID uuid, boolean enable) {
        BedwarsRelPlayer player = BedwarsRelCloudNetAddon.getInstance().getBedwarsRelPlayerManager().getBedwarsRelPlayer(uuid);
        if(player.hasVoteBefore() && player.isSameVote(enable)) return;
        player.setResourceEnableVote(enable);
        if(enable) {
            this.enabledVotes++;
            if(player.hasVoteBefore())this.disabledVotes--;
        } else {
            if(player.hasVoteBefore())this.enabledVotes--;
            this.disabledVotes++;
        }
        if(!player.hasVoteBefore()) player.setVoteBefore(true);
        setVotingInventoryContent();
    }

    /**
     * Remove vote of player and update inventory, if player has vote
     * @param uuid of leaved player
     */
    public void manageLeave(UUID uuid) {
        BedwarsRelPlayer bedwarsRelPlayer = BedwarsRelCloudNetAddon.getInstance().getBedwarsRelPlayerManager().getBedwarsRelPlayer(uuid);
        if(bedwarsRelPlayer.hasVoteBefore()) {
            if(bedwarsRelPlayer.isResourceEnableVote()) enabledVotes--;
            else disabledVotes--;
        }
        setVotingInventoryContent();
    }
}