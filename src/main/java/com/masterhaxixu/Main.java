package com.masterhaxixu;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
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
            World world = null;
            if (args[0].equalsIgnoreCase("world_the_end") || args[0].equalsIgnoreCase("world_nether") || args[0].equalsIgnoreCase("world")) {
                world = getServer().getWorld(args[0].toLowerCase());
            }
            for (World loadedWorld : world != null ? Collections.singletonList(world) : getServer().getWorlds()) {
                loadedWorld.getEntities().forEach(entity -> {
                    EntityType entityType = entity.getType();
                    entityCounts.put(entityType, entityCounts.getOrDefault(entityType, 0) + 1);
                });
            }
        } else {
                getServer().getWorlds().forEach(world -> {
                world.getEntities().forEach(entity -> {
                    EntityType entityType = entity.getType();
                    entityCounts.put(entityType, entityCounts.getOrDefault(entityType, 0) + 1);
                });
            });
            }
            String msg = "Entity Counts in $WORLD:";
            if(args.length > 0) sender.sendMessage(msg.replace("$WORLD", args[0]));
            else sender.sendMessage(msg.replace("$WORLD", "All Worlds"));
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
