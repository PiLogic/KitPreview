package com.seaneoconnor.kitpreview.listeners;

import com.seaneoconnor.kitpreview.api.EssAPI;
import com.seaneoconnor.kitpreview.KitPreview;
import com.seaneoconnor.kitpreview.Lang;
import com.sun.xml.internal.ws.util.StringUtils;
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
                p.sendMessage(plugin.references.getPrefix() + Lang.NO_PERM.getConfigValue(null));
            } else {
                String kitName = event.getLine(1);
                if (this.plugin.ess.getSettings().getKit(kitName.toLowerCase()) == null) {
                    event.getBlock().breakNaturally();
                    p.sendMessage(plugin.references.getPrefix() + Lang.KIT_DOESNT_EXIST.getConfigValue(kitName));
                } else {
                    event.setLine(0, Lang.SIGN_TITLE.getConfigValue(null));
                    event.setLine(1, StringUtils.capitalize(kitName));
                    p.sendMessage(plugin.references.getPrefix() + Lang.PREVIEW_SIGN_CREATED.getConfigValue(kitName));
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
                if (s.getLine(0).equals(Lang.SIGN_TITLE.getConfigValue(null))) {
                    if (!p.hasPermission("previewkit.sign.use")) {
                        p.sendMessage(plugin.references.getPrefix() + Lang.NO_PERM.getConfigValue(null));
                    } else {
                        String kitName = s.getLine(1);
                        p.sendMessage(plugin.references.getPrefix() + Lang.PREPARING_PREVIEW.getConfigValue(kitName));
                        EssAPI.displayKit(this.plugin.ess, p, kitName.toLowerCase());
                        p.sendMessage(plugin.references.getPrefix() + Lang.PREVIEWING_KIT.getConfigValue(kitName));
                    }
                }
            }
        }
    }
}

