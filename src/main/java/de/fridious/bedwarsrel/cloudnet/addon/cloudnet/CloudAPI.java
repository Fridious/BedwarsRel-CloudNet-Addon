package de.fridious.bedwarsrel.cloudnet.addon.cloudnet;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 08.12.18 20:32
 *
 */

public interface CloudAPI {

    void changeToIngame();

    void setMaxPlayers(int maxPlayers);

    void setMotd(String motd);

    void update();

}