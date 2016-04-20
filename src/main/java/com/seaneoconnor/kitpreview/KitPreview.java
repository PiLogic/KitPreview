package com.seaneoconnor.kitpreview;

import com.earth2me.essentials.Essentials;
import com.seaneoconnor.kitpreview.api.Metrics;
import com.seaneoconnor.kitpreview.commands.CommandKitPreview;
import com.seaneoconnor.kitpreview.commands.CommandPreviewKit;
import com.seaneoconnor.kitpreview.listeners.InventoryListeners;
import com.seaneoconnor.kitpreview.listeners.QuitListeners;
import com.seaneoconnor.kitpreview.listeners.SignListeners;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class KitPreview extends JavaPlugin {

    private ConfigWrapper langFile = new ConfigWrapper(this, "", "lang.yml");
    public References references = null;
    public Essentials ess = null;

    public boolean v1_8 = Bukkit.getServer().getClass().getPackage().getName().contains("1_8");
    public boolean v1_9 = Bukkit.getServer().getClass().getPackage().getName().contains("1_9");

    public List<UUID> xyz = new ArrayList<UUID>();

    public void onEnable() {
        ess = (Essentials) getServer().getPluginManager().getPlugin("Essentials");
        loadVersion();
        setupConfig();
        langFile.createNewFile("Loading language file", "KitPreview language file");
        loadLanguageFile();

        references = new References();

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
            Bukkit.getLogger().info("[KitPreview] Plugin Metrics enabled and loaded");
        } catch (IOException e) {
            Bukkit.getLogger().info("[KitPreview] Failed initialize Plugin Metrics");
        }

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

    private void loadVersion() {
        if (v1_8) {
            Bukkit.getLogger().info("[KitPreview] Plugin version set to 1.8");
        } else if (v1_9) {
            Bukkit.getLogger().info("[KitPreview] Plugin version set to 1.9");
        } else {
            Bukkit.getLogger().info("[KitPreview] Incompatible server version found: " + Bukkit.getServer().getClass().getPackage().getName());
            Bukkit.getLogger().info("[KitPreview] You must be running 1.8 or 1.9");
            this.setEnabled(false);
        }
    }

    private void setupConfig() {
        File f = new File(getDataFolder(), "config.yml");
        if (v1_9) {
            if (f.exists()) {
                saveConfig();
            } else {
                getConfig().addDefault("EnableSound", Boolean.valueOf(true));
                getConfig().addDefault("Sound", "ENTITY_ENDERMEN_TELEPORT");
                getConfig().options().copyDefaults(true);
                saveConfig();
            }
        } else {
            if (f.exists()) {
                saveConfig();
            } else {
                getConfig().addDefault("EnableSound", Boolean.valueOf(true));
                getConfig().addDefault("Sound", "ENDERMAN_TELEPORT");
                getConfig().options().copyDefaults(true);
                saveConfig();
            }
        }
    }

    private void loadLanguageFile() {
        Lang.setFile(langFile.getConfig());

        for (final Lang value : Lang.values()) {
            langFile.getConfig().addDefault(value.getPath(), value.getDefault());
        }

        langFile.getConfig().options().copyDefaults(true);
        langFile.saveConfig();
    }
}

