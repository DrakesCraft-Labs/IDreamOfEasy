package me.bunnky.idreamofeasy.listeners;

import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItem;
import me.bunnky.idreamofeasy.IDreamOfEasy;
import me.bunnky.idreamofeasy.slimefun.items.Magnetoid;
import me.bunnky.idreamofeasy.slimefun.tasks.MagnetoidTask;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MagnetoidListener implements Listener {

    public static final Map<UUID, BukkitTask> activeTasks = new HashMap<>();

    public MagnetoidListener(@Nonnull IDreamOfEasy plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        Bukkit.getScheduler().runTaskTimer(plugin, this::checkAllPlayersForMagnetoid, 0L, 10L);
    }

    private void checkAllPlayersForMagnetoid() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getGameMode() != GameMode.SPECTATOR) {
                handleMagnetoid(p);
            } else {
                cancelTask(p);
            }
        }
    }

    private void handleMagnetoid(Player p) {
        SlimefunItem sfItem = SlimefunItem.getByItem(p.getInventory().getItemInOffHand());
        if (sfItem instanceof Magnetoid magnetoid) {
            if (!activeTasks.containsKey(p.getUniqueId()) && magnetoid.canUse(p, true)) {
                BukkitTask task = new MagnetoidTask(p, magnetoid.getR(), magnetoid).runTaskTimer(IDreamOfEasy.getInstance(), 0L, 10L);
                activeTasks.put(p.getUniqueId(), task);
            }
        } else {
            cancelTask(p);
        }
    }

    public void cancelTask(Player p) {
        if (activeTasks.containsKey(p.getUniqueId())) {
            activeTasks.get(p.getUniqueId()).cancel();
            activeTasks.remove(p.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        cancelTask(e.getPlayer());
    }
}