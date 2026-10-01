package me.bunnky.idreamofeasy.slimefun.items;

import com.github.drakescraft_labs.slimefun4.api.events.PlayerRightClickEvent;
import com.github.drakescraft_labs.slimefun4.api.items.ItemGroup;
import com.github.drakescraft_labs.slimefun4.api.items.ItemSetting;
import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItemStack;
import com.github.drakescraft_labs.slimefun4.api.items.settings.IntRangeSetting;
import com.github.drakescraft_labs.slimefun4.api.recipes.RecipeType;
import com.github.drakescraft_labs.slimefun4.core.handlers.ItemUseHandler;
import com.github.drakescraft_labs.slimefun4.implementation.Slimefun;
import com.github.drakescraft_labs.slimefun4.implementation.items.SimpleSlimefunItem;
import me.bunnky.idreamofeasy.utils.IDOEUtility;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/*
A useful tool that points players toward the nearest biome of their choice, aiding exploration.
*/

public class BiomeCompass extends SimpleSlimefunItem<ItemUseHandler> {

    private final ItemSetting<Integer> r = new IntRangeSetting(this, "range", 1, 2500, Integer.MAX_VALUE);

    private static final int COOLDOWN_TICKS = 100; // 5 Segundos
    private final Biome[] biomes;

    private final Map<UUID, Integer> playerBiomeSelection = new ConcurrentHashMap<>();
    private final Map<UUID, Map<Biome, List<Location>>> playerBiomeCache = new ConcurrentHashMap<>();
    private final Map<UUID, Map<Biome, Location>> playerDiscoveredBiomes = new ConcurrentHashMap<>();

    public BiomeCompass(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        addItemSetting(r);
        IDOEUtility.setGlow(item);

        this.biomes = Biome.values();
    }

    @Override
    public @NotNull ItemUseHandler getItemHandler() {
        return this::onRightClick;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onRightClick(@NotNull PlayerRightClickEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();

        int selectedBiomeIndex = playerBiomeSelection.getOrDefault(uuid, 0);

        if (p.hasCooldown(Material.COMPASS)) {
            p.sendMessage("§cTienes que esperar un poco para volver a usarlo.");
            return;
        }

        if (p.isSneaking()) {
            if (e.getInteractEvent().getClickedBlock() == null) {
                selectedBiomeIndex = (selectedBiomeIndex + 1) % biomes.length;
                playerBiomeSelection.put(uuid, selectedBiomeIndex);
            } else {
                selectedBiomeIndex = (selectedBiomeIndex - 1 + biomes.length) % biomes.length;
                playerBiomeSelection.put(uuid, selectedBiomeIndex);
            }
            p.sendMessage("§eBioma elegido: " + ChatColor.GOLD + biomes[selectedBiomeIndex].name());
            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 0.5F, 1.5F);
            return;
        }

        Location closestBiomeLocation = findClosestBiome(p, p.getLocation(), biomes[selectedBiomeIndex]);
        if (closestBiomeLocation != null) {
            p.setCompassTarget(closestBiomeLocation);
            double distance = p.getLocation().distance(closestBiomeLocation);
            p.sendMessage("§a¡Encontrado " + ChatColor.GREEN + biomes[selectedBiomeIndex].name() + "§a a una distancia de " + ChatColor.GOLD + (int) distance + " §abloques!");
            Map<Biome, Location> playerDiscovered = playerDiscoveredBiomes.computeIfAbsent(uuid, k -> new ConcurrentHashMap<>());
            playerDiscovered.put(biomes[selectedBiomeIndex], closestBiomeLocation);
            p.setCooldown(Material.COMPASS, COOLDOWN_TICKS);
            p.playSound(p, Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 0.5F, 2F);
        } else {
            p.sendMessage("§cNo se detectó ningún " + ChatColor.DARK_RED + biomes[selectedBiomeIndex].name() + " §cen un radio de " + r.getValue() + " bloques.");
            p.setCooldown(Material.COMPASS, 40); // 2s en fallo
            p.playSound(p, Sound.BLOCK_ANVIL_LAND, SoundCategory.PLAYERS, 0.4F, 1F);
        }

        p.spawnParticle(Particle.EFFECT, p.getLocation().add(0, 1.0, 0), 25, 0.3, 0.3, 0.3, 0.05);
    }

    private Location findClosestBiome(Player p, Location playerLocation, Biome targetBiome) {
        World world = playerLocation.getWorld();
        if (world == null) {
            return null;
        }

        int radius = Math.max(500, r.getValue());
        Location nearest = null;

        // Paper API nativa: búsqueda por noise sampling en vez de iterar 40,000 bloques
        try {
            nearest = world.locateNearestBiome(playerLocation, targetBiome, radius, 32);
        } catch (Throwable t) {
            try {
                nearest = world.locateNearestBiome(playerLocation, targetBiome, radius);
            } catch (Throwable ignored) {}
        }

        UUID uuid = p.getUniqueId();
        if (nearest != null) {
            try {
                int highestY = world.getHighestBlockYAt(nearest.getBlockX(), nearest.getBlockZ());
                nearest.setY(Math.max(64, highestY));
            } catch (Throwable ignored) {}

            Map<Biome, List<Location>> playerCache = playerBiomeCache.computeIfAbsent(uuid, k -> new ConcurrentHashMap<>());
            List<Location> list = playerCache.computeIfAbsent(targetBiome, k -> new ArrayList<>());

            boolean alreadyCached = false;
            for (Location loc : list) {
                if (loc.getWorld() != null && loc.getWorld().equals(world) && loc.distanceSquared(nearest) < 10000) {
                    alreadyCached = true;
                    break;
                }
            }
            if (!alreadyCached) {
                list.add(nearest);
            }

            return nearest;
        }

        // Si no se encuentra dentro del radio, buscar en descubrimientos previos del jugador
        Map<Biome, List<Location>> playerCache = playerBiomeCache.get(uuid);
        if (playerCache != null) {
            List<Location> cachedLocations = playerCache.get(targetBiome);
            if (cachedLocations != null && !cachedLocations.isEmpty()) {
                Location closestCached = null;
                double closestCachedDistance = Double.MAX_VALUE;

                for (Location loc : cachedLocations) {
                    if (loc.getWorld() != null && loc.getWorld().equals(world)) {
                        double distance = loc.distance(playerLocation);
                        if (distance < closestCachedDistance) {
                            closestCachedDistance = distance;
                            closestCached = loc;
                        }
                    }
                }

                return closestCached;
            }
        }
        return null;
    }

    public static int getRange() {
        return Slimefun.getItemCfg().getOrSetDefault("IDOE_BIOMECOMPASS.range", 2500);
    }
}
