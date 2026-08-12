package me.bunnky.idreamofeasy.slimefun.tasks;

import com.github.drakescraft_labs.slimefun4.utils.SlimefunUtils;
import me.bunnky.idreamofeasy.IDreamOfEasy;
import me.bunnky.idreamofeasy.slimefun.items.Magnetoid;
import me.bunnky.idreamofeasy.listeners.MagnetoidListener;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class MagnetoidTask extends BukkitRunnable {

    private static final float COST = 0.01F;

    private final double r;
    private final Player p;
    private final Magnetoid magnetoid;

    public MagnetoidTask(@NotNull Player p, double r, @NotNull Magnetoid magnetoid) {
        this.r = r;
        this.p = p;
        this.magnetoid = magnetoid;
    }

    @Override
    public void run() {
        if (!isValid()) {
            cancel();
            return;
        }

        ItemStack off = p.getInventory().getItemInOffHand();
        if (!SlimefunUtils.isItemSimilar(off, magnetoid.getItem(), true)) {
            MagnetoidListener.activeTasks.remove(p.getUniqueId());
            cancel();
            return;
        }

        if (!p.isSneaking()) {
            for (Entity ent : p.getNearbyEntities(r, r, r)) {
                if (ent instanceof Item i && isValidItem(i)) {
                    if (!i.hasMetadata("tp")) {
                        if (magnetoid.removeItemCharge(off, COST)) {
                            i.teleport(p.getLocation());
                            p.playSound(p, Sound.BLOCK_AMETHYST_BLOCK_STEP, SoundCategory.PLAYERS, 0.2F, 0.2F);
                            i.setMetadata("tp", new FixedMetadataValue(IDreamOfEasy.getInstance(), true));
                            Bukkit.getScheduler().runTaskLater(IDreamOfEasy.getInstance(), () -> i.removeMetadata("tp", IDreamOfEasy.getInstance()), 10L);
                        }
                    }
                }
            }
        }
    }

    protected boolean isValid() {
        return p.isOnline() || p.getGameMode() != GameMode.SPECTATOR;
    }

    private boolean isValidItem(Item i) {
        return !i.isDead() && !SlimefunUtils.hasNoPickupFlag(i) && i.getPickupDelay() <= 0 && p.getLocation().distanceSquared(i.getLocation()) > 0.3;
    }
}