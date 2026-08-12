package me.bunnky.idreamofeasy.listeners;

import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItem;
import me.bunnky.idreamofeasy.IDreamOfEasy;
import me.bunnky.idreamofeasy.slimefun.items.idols.TerranIdol;
import me.bunnky.idreamofeasy.slimefun.items.idols.Idol;
import me.bunnky.idreamofeasy.slimefun.items.idols.DivineIdol;
import me.bunnky.idreamofeasy.slimefun.items.idols.TorrentIdol;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class IdolListener implements Listener {

    public IdolListener(@Nonnull IDreamOfEasy plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player p) {
            Set<Class<? extends Idol>> triggeredIdols = new HashSet<>();
            for (Idol idol : Idol.getIdols()) {
                if (triggeredIdols.add(idol.getClass())) {
                    idol.trigger(p, e);
                }
            }
        } else if (e.getEntity() instanceof Item item) {
            SlimefunItem sfItem = SlimefunItem.getByItem(item.getItemStack());
            if (sfItem instanceof Idol) {
                Vector velocity = e.getEntity().getVelocity();
                e.getEntity().setVelocity(new Vector(velocity.getX(), 0.1, velocity.getZ()));
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            Player p = e.getEntity().getKiller();
            if (p != null) {
                Set<Class<? extends Idol>> triggeredIdols = new HashSet<>();
                for (Idol idol : Idol.getIdols()) {
                    if (idol instanceof TerranIdol && triggeredIdols.add(idol.getClass())) {
                        idol.trigger(p, e);
                        break;
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e) {
        Set<Class<? extends Idol>> triggeredIdols = new HashSet<>();
        for (Idol idol : Idol.getIdols()) {
            if (idol instanceof TerranIdol && triggeredIdols.add(idol.getClass())) {
                idol.trigger(e.getPlayer(), e);
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEnchantItem(EnchantItemEvent e) {
        Set<Class<? extends Idol>> triggeredIdols = new HashSet<>();
        for (Idol idol : Idol.getIdols()) {
            if (idol instanceof DivineIdol && triggeredIdols.add(idol.getClass())) {
                idol.trigger(e.getEnchanter(), e);
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerExpChange(PlayerExpChangeEvent e) {
        Set<Class<? extends Idol>> triggeredIdols = new HashSet<>();
        for (Idol idol : Idol.getIdols()) {
            if (idol instanceof DivineIdol && triggeredIdols.add(idol.getClass())) {
                idol.trigger(e.getPlayer(), e);
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerItemDamage(PlayerItemDamageEvent e) {
        Set<Class<? extends Idol>> triggeredIdols = new HashSet<>();
        for (Idol idol : Idol.getIdols()) {
            if (idol instanceof DivineIdol && triggeredIdols.add(idol.getClass())) {
                idol.trigger(e.getPlayer(), e);
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerSprint(PlayerToggleSprintEvent e) {
        Set<Class<? extends Idol>> triggeredIdols = new HashSet<>();
        for (Idol idol : Idol.getIdols()) {
            if (idol instanceof TorrentIdol && triggeredIdols.add(idol.getClass())) {
                idol.trigger(e.getPlayer(), e);
                break;
            }
        }
    }
}


