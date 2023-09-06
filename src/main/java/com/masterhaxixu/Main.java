package com.masterhaxixu;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("EntityCountPlugin has been enabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Map<EntityType, Integer> entityCounts = new HashMap<>();
        if (cmd.getName().equalsIgnoreCase("entitycounter")) {
            if (args.length > 0) {
                World world = getServer().getWorlds().stream()
                        .filter(w -> args[0].equalsIgnoreCase(w.getName()))
                        .findFirst()
                        .orElse(null);
                if (world != null) {

                    world.getEntities().forEach(entity -> {
                        EntityType entityType = entity.getType();
                        entityCounts.put(entityType, entityCounts.getOrDefault(entityType, 0) + 1);
                    });

                } else
                    sender.sendMessage("No world with the name " + ChatColor.RED + args[0] + " was found!");

            } else {
                getServer().getWorlds().forEach(world -> {
                    world.getEntities().forEach(entity -> {
                        EntityType entityType = entity.getType();
                        entityCounts.put(entityType, entityCounts.getOrDefault(entityType, 0) + 1);
                    });
                });
            }
            String msg = "Entity Counts in $WORLD:";
            if (args.length > 0)
                sender.sendMessage(msg.replace("$WORLD", args[0]));
            else
                sender.sendMessage(msg.replace("$WORLD", "All Worlds"));
            for (Map.Entry<EntityType, Integer> entry : entityCounts.entrySet()) {
                sender.sendMessage(entry.getKey().name() + ": " + entry.getValue());
            }
            return true;
        }
        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("entitycounter")) {
            List<String> completions = new ArrayList<>();
            for (org.bukkit.World world : getServer().getWorlds()) {
                completions.add(world.getName());
            }
            return completions;
        }
        return null;
    }
}
