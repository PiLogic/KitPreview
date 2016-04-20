package com.seaneoconnor.kitpreview.listeners;

import com.seaneoconnor.kitpreview.KitPreview;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListeners implements Listener {

    KitPreview plugin = KitPreview.pl();

    @EventHandler
    public void quit(PlayerQuitEvent event) {
        if (plugin.xyz.contains(event.getPlayer().getUniqueId())) {
            plugin.xyz.remove(event.getPlayer().getUniqueId());
        }
    }
}

