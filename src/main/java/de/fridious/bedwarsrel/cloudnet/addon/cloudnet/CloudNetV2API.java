package de.fridious.bedwarsrel.cloudnet.addon.cloudnet;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 08.12.18 20:32
 *
 */

import de.dytanic.cloudnet.bridge.CloudServer;

public class CloudNetV2API implements CloudAPI {

    @Override
    public void changeToIngame() {
        CloudServer.getInstance().changeToIngame();
    }

    @Override
    public void setMaxPlayers(int maxPlayers) {
        CloudServer.getInstance().setMaxPlayers(maxPlayers);
    }

    @Override
    public void setMotd(String motd) {
        CloudServer.getInstance().setMotd(motd);
    }

    @Override
    public void update() {
        CloudServer.getInstance().updateAsync();
    }
}