package de.fridious.bedwarsrel.cloudnet.addon.cloudnet;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 08.12.18 20:33
 *
 */

import de.dytanic.cloudnet.ext.bridge.bukkit.BukkitCloudNetHelper;

public class CloudNetV3API implements CloudAPI {

    @Override
    public void changeToIngame() {
        BukkitCloudNetHelper.changeToIngame();
    }

    @Override
    public void setMaxPlayers(int maxPlayers) {
        BukkitCloudNetHelper.setMaxPlayers(maxPlayers);
    }

    @Override
    public void setMotd(String motd) {
        BukkitCloudNetHelper.setApiMotd(motd);
    }

    @Override
    public void update() {
        BukkitCloudNetHelper.updateServiceInfo();
    }
}