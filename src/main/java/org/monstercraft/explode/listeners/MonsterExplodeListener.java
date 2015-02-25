package org.monstercraft.explode.listeners;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;
import org.monstercraft.explode.MonsterExplode;
import org.monstercraft.explode.util.Variables;

import com.malikk.shield.Shield;
import com.malikk.shield.ShieldAPI;

public class MonsterExplodeListener extends MonsterExplode implements Listener {

    private final Random ran = new Random();
    private ShieldAPI api = null;
    ArrayList<Location> locations = new ArrayList<Location>();

    public MonsterExplodeListener(final MonsterExplode plugin) {
        final Plugin x = plugin.getServer().getPluginManager()
                .getPlugin("Shield");
        if (x != null && x instanceof Shield) {
            final Shield shield = (Shield) x;
            api = shield.getAPI();
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDeath(final EntityDeathEvent event) {
        final int i = ran.nextInt(100);
        final Entity entity = event.getEntity();
        if (!(entity instanceof Player)) {
            return;
        }
        final Player player = (Player) entity;
        final Location loc = player.getLocation();
        if (!player.hasPermission("monsterexplode.explode")) {
            return;
        }
        if (loc == null) {
            return;
        }
        if (!Variables.enabled) {
            player.sendMessage(ChatColor.RED
                    + "Exploding on death is currently disabled.");
            return;
        }
        if (api != null && loc != null) {
            if (api.isInRegion(loc)) {
                player.sendMessage(ChatColor.RED
                        + "You could not explode withn the protection region!");
                return;
            }
        }
        if (i <= Variables.randomnessFactor) {
            final World world = player.getWorld();
            if (Variables.prevent_block_damage) {
                locations.add(loc);
            }
            world.createExplosion(loc, Variables.size, true);
            player.sendMessage(ChatColor.RED + "You have exploded!");
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onExplode(final EntityExplodeEvent event) {
        final Location loc = event.getLocation();
        if (Variables.prevent_block_damage) {
            for (final Location location : locations) {
                if (location.getX() == loc.getX()
                        && location.getY() == loc.getY()
                        && loc.getZ() == location.getZ()
                        && loc.getWorld() == location.getWorld()) {
                    event.blockList().clear();
                    locations.remove(location);
                    return;
                }
            }
        }
    }
}