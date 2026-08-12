package me.bunnky.idreamofeasy.slimefun.machines;

import com.github.drakescraft_labs.slimefun4.api.items.ItemGroup;
import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItem;
import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItemStack;
import com.github.drakescraft_labs.slimefun4.api.recipes.RecipeType;

import com.github.drakescraft_labs.slimefun4.core.attributes.EnergyNetComponent;
import com.github.drakescraft_labs.slimefun4.core.handlers.BlockBreakHandler;
import com.github.drakescraft_labs.slimefun4.core.networks.energy.EnergyNetComponentType;
import com.github.drakescraft_labs.slimefun4.implementation.Slimefun;
import com.github.drakescraft_labs.slimefun4.implementation.tasks.armor.RadiationTask;
import com.github.drakescraft_labs.slimefun4.libraries.dough.protection.Interaction;
import com.github.drakescraft_labs.slimefun4.utils.RadiationUtils;
import me.bunnky.idreamofeasy.IDreamOfEasy;
import me.bunnky.idreamofeasy.utils.IDOEUtility;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import com.github.drakescraft_labs.slimefun4.legacy.Objects.handlers.BlockTicker;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
/*
Protect yourself from harmful radiation. This device absorbs radiation in a radius, granting players temporary immunity while powered.
 */
public class RadiationAbsorber extends SlimefunItem implements EnergyNetComponent {

    private static final int GRACE_PERIOD = Slimefun.getCfg().getInt("options.radiation-grace-period");
    private final Map<UUID, Long> cd = new HashMap<>();
    private final Map<UUID, BossBar> bars = new HashMap<>();
    private static final Set<UUID> prot = new HashSet<>();
    private final int cap;
    private final int ecost;
    private BukkitRunnable cdTask;

    public RadiationAbsorber(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int ecost, int cap, int range) {
        super(itemGroup, item, recipeType, recipe);

        this.cap = cap;
        this.ecost = ecost;

        addItemHandler(new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(@NotNull BlockBreakEvent e, @NotNull ItemStack stack, @NotNull List<ItemStack> list) {

                for (UUID pUUID : prot) {
                    Player p = Bukkit.getPlayer(pUUID);
                    if (p != null ) {
                        BossBar bar = bars.get(pUUID);
                        if (bar != null) {
                            bar.removePlayer(p);
                        }
                        prot.remove(pUUID);
                        bars.remove(pUUID);
                    }
                }

                if (cdTask != null) {
                    cdTask.cancel();
                    cdTask = null;
                }
            }
        });

        addItemHandler(new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return false;
            }

            @Override
            public void tick(Block b, SlimefunItem sfItem, Config config) {
                Location loc = b.getLocation();
                World w = loc.getWorld();

                if (w == null || getCharge(loc) < ecost) {
                    return;
                }
                nearbyPlayers(loc, range);
            }
        });
    }

    private void nearbyPlayers(Location loc, int range) {
        Bukkit.getScheduler().runTask(IDreamOfEasy.getInstance(), () -> {
            for (Player p : loc.getNearbyPlayers(range)) {
                if (Slimefun.getProtectionManager().hasPermission(p, loc, Interaction.INTERACT_BLOCK)) {
                    if (eligible(p)) {
                        if (getCharge(loc) >= ecost) {
                            protect(loc, p);
                            IDOEUtility.spawnBeam(p, loc);
                        }
                    }
                }
            }
        });
    }

    private boolean eligible(Player p) {
        UUID pUUID = p.getUniqueId();
        long curr = System.currentTimeMillis();

        return p.getGameMode() == GameMode.SURVIVAL &&
            (!cd.containsKey(pUUID) || curr - cd.get(pUUID) >= GRACE_PERIOD * 1000L) &&
            !prot.contains(pUUID);
    }

    private void protect(Location loc, Player p) {
        UUID pUUID = p.getUniqueId();
        long curr = System.currentTimeMillis();

        RadiationTask.addGracePeriod(p);
        RadiationUtils.clearExposure(p);
        RadiationUtils.removeExposure(p, RadiationUtils.getExposure(p));

        cd.put(pUUID, curr);
        prot.add(pUUID);

        BossBar bar = bars.computeIfAbsent(pUUID, uuid -> IDOEUtility.createBossBar(p));
        countdown(loc, bar, p);
    }

    private void countdown(Location loc, BossBar bar, Player p) {
        cdTask = new BukkitRunnable() {

            long cd = GRACE_PERIOD;

            @Override
            public void run() {
                removeCharge(loc, ecost);
                loc.getWorld().spawnParticle(Particle.ITEM_SLIME, loc.clone().add(0.5, 0.5, 0.5), 5, 0.5, 0.5, 0.5, 0);
                double progress = (double) cd / GRACE_PERIOD;
                bar.setProgress(progress);

                if (cd > 0) {
                    cd--;
                } else {
                    bar.removeAll();
                    prot.remove(p.getUniqueId());
                    bars.remove(p.getUniqueId());
                    cancel();
                }
            }
        };
        cdTask.runTaskTimer(IDreamOfEasy.getInstance(), 0L, 20L);
    }

    @Override
    public @NotNull EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return this.cap;
    }
}