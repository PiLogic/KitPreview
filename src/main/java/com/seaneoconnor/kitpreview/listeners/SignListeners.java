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

public class SignListeners implements Listener {

    KitPreview plugin = KitPreview.pl();

    @EventHandler
    public void onCreateSign(SignChangeEvent event) {
        Player p = event.getPlayer();
        if (event.getLine(0).equalsIgnoreCase("[previewkit]")) {
            if (!p.hasPermission("previewkit.sign.create")) {
                event.getBlock().breakNaturally();
                p.sendMessage(plugin.references.getPrefix() + ChatColor.RED + "You do not have permission to create a preview sign.");
            } else {
                String kitName = event.getLine(1);
                if (this.plugin.ess.getSettings().getKit(kitName.toLowerCase()) == null) {
                    event.getBlock().breakNaturally();
                    p.sendMessage(plugin.references.getPrefix() + ChatColor.RED + "That kit does not exist.");
                } else {
                    event.setLine(0, ChatColor.DARK_BLUE + "[PreviewKit]");
                    event.setLine(1, StringUtils.capitalize(kitName));
                    p.sendMessage(plugin.references.getPrefix() + ChatColor.GREEN + "Preview sign created.");
                }
            }
        }
    }

    @EventHandler
    public void onClickSign(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if ((event.getClickedBlock().getState() instanceof Sign)) {
                Sign s = (Sign) event.getClickedBlock().getState();
                if (s.getLine(0).equals(ChatColor.DARK_BLUE + "[PreviewKit]")) {
                    String kitName = s.getLine(1);
                    p.sendMessage(plugin.references.getPrefix() + ChatColor.GRAY + "Preparing preview...");
                    EssAPI.displayKit(this.plugin.ess, p, kitName.toLowerCase());
                    p.sendMessage(plugin.references.getPrefix() + ChatColor.GREEN + "You are now previewing the " + StringUtils.capitalize(kitName) + " kit.");
                }
            }
        }
    }
}

