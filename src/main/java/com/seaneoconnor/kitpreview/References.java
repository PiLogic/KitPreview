package com.seaneoconnor.kitpreview;

import org.bukkit.ChatColor;
import org.bukkit.Sound;

public class References {

    private String prefix = null;
    private boolean playSound = false;
    private Sound sound = null;

    public References() {
        prefix = ChatColor.translateAlternateColorCodes('&', KitPreview.pl().getConfig().getString("Prefix") + " ");
        playSound = KitPreview.pl().getConfig().getBoolean("EnableSound");
        sound = Sound.valueOf(KitPreview.pl().getConfig().getString("Sound"));
    }

    public String getPrefix() {
        return this.prefix;
    }

    public boolean isPlaySound() {
        return this.playSound;
    }

    public Sound getSound() {
        return this.sound;
    }
}
