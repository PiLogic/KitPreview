package com.seaneoconnor.kitpreview.listeners;

import com.seaneoconnor.kitpreview.EssAPI;
import com.seaneoconnor.kitpreview.KitPreview;
import com.sun.xml.internal.ws.util.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
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

