package com.seaneoconnor.kitpreview.commands;

import com.seaneoconnor.kitpreview.EssAPI;
import com.seaneoconnor.kitpreview.KitPreview;
import com.sun.xml.internal.ws.util.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPreviewKit implements CommandExecutor {

    KitPreview plugin = KitPreview.pl();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("previewkit")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(plugin.references.getPrefix() + ChatColor.RED + "This command can only be used by players.");
            } else {
                Player player = (Player) sender;
                if (!player.hasPermission("previewkit.use")) {
                    player.sendMessage(plugin.references.getPrefix() + ChatColor.RED + "You do not have access to \"/" + label + "\"");
                } else {
                    if (args.length != 1) {
                        player.sendMessage(plugin.references.getPrefix() + ChatColor.GRAY + "Please include a kit to preview.");
                    } else {
                        String kit = args[0].toLowerCase();
                        if (plugin.ess.getSettings().getKit(kit) == null) {
                            player.sendMessage(plugin.references.getPrefix() + ChatColor.RED + "That kit does not exist.");
                        } else {
                            player.sendMessage(plugin.references.getPrefix() + ChatColor.GRAY + "Preparing preview...");
                            EssAPI.displayKit(plugin.ess, player, kit);
                            player.sendMessage(plugin.references.getPrefix() + ChatColor.GREEN + "You are now previewing kit \"" + StringUtils.capitalize(kit) + "\"");
                        }
                    }
                }
            }
        }
        return true;
    }
}
