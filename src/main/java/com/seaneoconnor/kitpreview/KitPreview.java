package com.seaneoconnor.kitpreview;

import com.earth2me.essentials.Essentials;
import com.seaneoconnor.kitpreview.commands.CommandKitPreview;
import com.seaneoconnor.kitpreview.commands.CommandPreviewKit;
import com.seaneoconnor.kitpreview.listeners.InventoryListeners;
import com.seaneoconnor.kitpreview.listeners.QuitListeners;
import com.seaneoconnor.kitpreview.listeners.SignListeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class KitPreview extends JavaPlugin {

    public References references = null;
    public Essentials ess = null;

    public boolean v1_8 = Bukkit.getServer().getClass().getPackage().getName().contains("1_8");
    public boolean v1_9 = Bukkit.getServer().getClass().getPackage().getName().contains("1_9");

    public List<UUID> xyz = new ArrayList<UUID>();

    public void onEnable() {
        setupEssentials();
        loadVersion();
        setupConfig();

        references = new References();

        getCommand("kitpreview").setExecutor(new CommandKitPreview());
        getCommand("previewkit").setExecutor(new CommandPreviewKit());
        getServer().getPluginManager().registerEvents(new InventoryListeners(), this);
        getServer().getPluginManager().registerEvents(new SignListeners(), this);
        getServer().getPluginManager().registerEvents(new QuitListeners(), this);
    }

    public void onDisable() {
        xyz.clear();
    }

    public static KitPreview pl() {
        return (KitPreview) Bukkit.getServer().getPluginManager().getPlugin("KitPreview");
    }

    private void setupEssentials() {
        if (getServer().getPluginManager().getPlugin("Essentials") != null) {
            ess = (Essentials) getServer().getPluginManager().getPlugin("Essentials");
        } else {
            getLogger().warning("[KitPreview] You need Essentials to run this plugin.");
            getLogger().warning("[KitPreview] https://www.spigotmc.org/resources/essentialsx.9089/");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void loadVersion() {
        if (v1_8) {
            getLogger().info("[KitPreview] Plugin version set to 1.8");
        } else if (v1_9) {
            getLogger().info("[KitPreview] Plugin version set to 1.9");
        } else {
            getLogger().warning("[KitPreview] You must be running Spigot 1.8 or 1.9! Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void setupConfig() {
        File f = new File(getDataFolder(), "config.yml");
        if (v1_9) {
            if (f.exists()) {
                saveConfig();
            } else {
                getConfig().addDefault("Prefix", "&6KitPreview>");
                getConfig().addDefault("EnableSound", Boolean.valueOf(true));
                getConfig().addDefault("Sound", "ENTITY_ENDERMEN_TELEPORT");
                getConfig().options().copyDefaults(true);
                saveConfig();
            }
        } else {
            if (f.exists()) {
                saveConfig();
            } else {
                getConfig().addDefault("Prefix", "&6KitPreview>");
                getConfig().addDefault("EnableSound", Boolean.valueOf(true));
                getConfig().addDefault("Sound", "ENDERMAN_TELEPORT");
                getConfig().options().copyDefaults(true);
                saveConfig();
            }
        }
    }
}

