package de.fridious.bedwarsrel.cloudnet.addon.events;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 04.12.18 17:01
 *
 */

import io.github.bedwarsrel.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called on bedwars final kill
 */
public class BedwarsPlayerFinalKilledEvent extends Event {

    /*
     * Fields for handlers, game, killer and player
     */
    private static final HandlerList handlerList = new HandlerList();
    private Game game;
    private Player killer, player;

    /**
     * Constructor to call this event
     * @param game of bedwars
     * @param player, which was killed
     * @param killer, which has killed the player (Can be null, if player has killed himself
     */
    public BedwarsPlayerFinalKilledEvent(Game game, Player player, Player killer) {
        this.player = player;
        this.killer = killer;
        this.game = game;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public HandlerList getHandlers() {
        return handlerList;
    }

    public Game getGame() {
        return this.game;
    }

    public Player getKiller() {
        return this.killer;
    }

    public Player getPlayer() {
        return this.player;
    }
}