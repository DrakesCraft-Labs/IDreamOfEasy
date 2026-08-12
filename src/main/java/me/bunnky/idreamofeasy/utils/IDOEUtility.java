package me.bunnky.idreamofeasy.utils;

import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class IDOEUtility {

    public static final String PREFIX = "§2[IDreamOfEasy]§a ";
    private static final Map<Material, Integer> templateMap = createTemplateMap();
    private static final Map<EntityType, ItemStack> entityDropMap = createEntityDropMap();

    private static Map<Material, Integer> createTemplateMap() {
        Map<Material, Integer> map = new HashMap<>();

        map.put(Material.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, 10);
        map.put(Material.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, 10);
        map.put(Material.HOST_ARMOR_TRIM_SMITHING_TEMPLATE, 10);
        map.put(Material.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE, 10);
        map.put(Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, 7);
        map.put(Material.COAST_ARMOR_TRIM_SMITHING_TEMPLATE, 7);
        map.put(Material.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, 7);
        map.put(Material.EYE_ARMOR_TRIM_SMITHING_TEMPLATE, 6);
        map.put(Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, 6);
        map.put(Material.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, 6);
        map.put(Material.RIB_ARMOR_TRIM_SMITHING_TEMPLATE, 6);
        map.put(Material.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, 4);
        map.put(Material.VEX_ARMOR_TRIM_SMITHING_TEMPLATE, 4);
        map.put(Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, 3);
        map.put(Material.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, 2);
        map.put(Material.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, 2);
        map.put(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1);
        return map;
    }

    public static ItemStack getRandomTemplate() {
        int totalWeight = templateMap.values().stream().mapToInt(i -> i).sum();
        int random = new Random().nextInt(totalWeight);
        int current = 0;

        for (Map.Entry<Material, Integer> entry : templateMap.entrySet()) {
            current += entry.getValue();
            if (random < current) {
                return new ItemStack(entry.getKey());
            }
        }
        return null;
    }

    private static Map<EntityType, ItemStack> createEntityDropMap() {
        Map<EntityType, ItemStack> map = new HashMap<>();

        // PASSIVE
        map.put(EntityType.ALLAY, new ItemStack(Material.AMETHYST_SHARD, 2));
        map.put(EntityType.ARMADILLO, new ItemStack(Material.ARMADILLO_SCUTE, 2));
        map.put(EntityType.AXOLOTL, new ItemStack(Material.FLOWERING_AZALEA, 2));
        map.put(EntityType.BAT, new ItemStack(Material.HANGING_ROOTS, 2));
        map.put(EntityType.CAMEL, new ItemStack(Material.BEEF, 2));
        map.put(EntityType.CAT, new ItemStack(Material.SALMON, 2));
        map.put(EntityType.CHICKEN, new ItemStack(Material.FEATHER, 2));
        map.put(EntityType.COD, new ItemStack(Material.SEAGRASS, 2));
        map.put(EntityType.COW, new ItemStack(Material.BEEF, 2));
        map.put(EntityType.DONKEY, new ItemStack(Material.BEEF, 2));
        map.put(EntityType.FROG, new ItemStack(Material.FROGSPAWN, 2));
        map.put(EntityType.GLOW_SQUID, new ItemStack(Material.GLOW_INK_SAC, 2));
        map.put(EntityType.HORSE, new ItemStack(Material.BEEF, 2));
        map.put(EntityType.MOOSHROOM, new ItemStack(Material.RED_MUSHROOM, 2));
        map.put(EntityType.MULE, new ItemStack(Material.BEEF, 2));
        map.put(EntityType.OCELOT, new ItemStack(Material.COD, 2));
        map.put(EntityType.PARROT, new ItemStack(Material.FEATHER, 2));
        map.put(EntityType.PIG, new ItemStack(Material.PORKCHOP, 2));
        map.put(EntityType.PUFFERFISH, new ItemStack(Material.SEAGRASS, 2));
        map.put(EntityType.RABBIT, new ItemStack(Material.RABBIT_HIDE, 2));
        map.put(EntityType.SALMON, new ItemStack(Material.SEAGRASS, 2));
        map.put(EntityType.SHEEP, new ItemStack(Material.WHITE_WOOL, 2));
        map.put(EntityType.SKELETON_HORSE, new ItemStack(Material.BONE, 4));
        map.put(EntityType.SNIFFER, new ItemStack(Material.TORCHFLOWER_SEEDS, 2));
        map.put(EntityType.SNOW_GOLEM, new ItemStack(Material.SNOWBALL, 2));
        map.put(EntityType.SQUID, new ItemStack(Material.INK_SAC, 2));
        map.put(EntityType.STRIDER, new ItemStack(Material.MAGMA_CREAM, 2));
        map.put(EntityType.TADPOLE, new ItemStack(Material.FROGSPAWN, 2));
        map.put(EntityType.TROPICAL_FISH, new ItemStack(Material.SEAGRASS, 2));
        map.put(EntityType.TURTLE, new ItemStack(Material.TURTLE_SCUTE, 2));
        map.put(EntityType.VILLAGER, new ItemStack(Material.LEATHER_BOOTS, 2));
        map.put(EntityType.WANDERING_TRADER, new ItemStack(Material.SADDLE, 2));

        // NEUTRAL
        map.put(EntityType.BEE, new ItemStack(Material.HONEYCOMB, 2));
        map.put(EntityType.CAVE_SPIDER, new ItemStack(Material.SPIDER_EYE, 2));
        map.put(EntityType.DOLPHIN, new ItemStack(Material.KELP, 2));
        map.put(EntityType.DROWNED, new ItemStack(Material.ROTTEN_FLESH, 2));
        map.put(EntityType.ENDERMAN, new ItemStack(Material.ENDER_PEARL, 2));
        map.put(EntityType.FOX, new ItemStack(Material.SWEET_BERRIES, 2));
        map.put(EntityType.GOAT, new ItemStack(Material.GOAT_HORN, 2));
        map.put(EntityType.IRON_GOLEM, new ItemStack(Material.IRON_INGOT, 2));
        map.put(EntityType.LLAMA, new ItemStack(Material.LEATHER, 2));
        map.put(EntityType.PANDA, new ItemStack(Material.BAMBOO, 2));
        map.put(EntityType.PIGLIN, new ItemStack(Material.GOLD_INGOT, 2));
        map.put(EntityType.POLAR_BEAR, new ItemStack(Material.COD, 2));
        map.put(EntityType.SPIDER, new ItemStack(Material.COBWEB, 2));
        map.put(EntityType.TRADER_LLAMA, new ItemStack(Material.SADDLE, 2));
        map.put(EntityType.WOLF, new ItemStack(Material.BONE, 2));
        map.put(EntityType.ZOMBIFIED_PIGLIN, new ItemStack(Material.ROTTEN_FLESH, 2));

        // HOSTILE
        map.put(EntityType.BLAZE, new ItemStack(Material.BLAZE_ROD, 2));
        map.put(EntityType.CREEPER, new ItemStack(Material.GUNPOWDER, 2));
        map.put(EntityType.ELDER_GUARDIAN, new ItemStack(Material.HEART_OF_THE_SEA, 2));
        map.put(EntityType.ENDERMITE, new ItemStack(Material.ENDER_PEARL, 2));
        map.put(EntityType.EVOKER, new ItemStack(Material.EMERALD, 2));
        map.put(EntityType.GHAST, new ItemStack(Material.GHAST_TEAR, 2));
        map.put(EntityType.GUARDIAN, new ItemStack(Material.PRISMARINE_SHARD, 2));
        map.put(EntityType.HOGLIN, new ItemStack(Material.BEEF, 2));
        map.put(EntityType.HUSK, new ItemStack(Material.SAND, 2));
        map.put(EntityType.MAGMA_CUBE, new ItemStack(Material.MAGMA_CREAM, 2));
        map.put(EntityType.PHANTOM, new ItemStack(Material.PHANTOM_MEMBRANE, 2));
        map.put(EntityType.PIGLIN_BRUTE, new ItemStack(Material.GOLD_BLOCK, 2));
        map.put(EntityType.PILLAGER, new ItemStack(Material.BOW, 2));
        map.put(EntityType.RAVAGER, new ItemStack(Material.BEEF, 2));
        map.put(EntityType.SHULKER, new ItemStack(Material.SHULKER_SHELL, 2));
        map.put(EntityType.SILVERFISH, new ItemStack(Material.GRAY_DYE, 2));
        map.put(EntityType.SKELETON, new ItemStack(Material.BONE, 2));
        map.put(EntityType.SLIME, new ItemStack(Material.SLIME_BALL, 2));
        map.put(EntityType.STRAY, new ItemStack(Material.BONE, 2));
        map.put(EntityType.VEX, new ItemStack(Material.IRON_SWORD, 2));
        map.put(EntityType.VINDICATOR, new ItemStack(Material.SHIELD, 2));
        map.put(EntityType.WARDEN, new ItemStack(Material.SOUL_TORCH, 2));
        map.put(EntityType.WITCH, new ItemStack(Material.POISONOUS_POTATO, 2));
        map.put(EntityType.WITHER_SKELETON, new ItemStack(Material.WITHER_SKELETON_SKULL, 2));
        map.put(EntityType.ZOGLIN, new ItemStack(Material.ROTTEN_FLESH, 2));
        map.put(EntityType.ZOMBIE, new ItemStack(Material.ROTTEN_FLESH, 2));
        map.put(EntityType.ZOMBIE_VILLAGER, new ItemStack(Material.ROTTEN_FLESH, 2));

        return map;
    }

    public static ItemStack getEntityDrop(EntityType entityType) {
        return entityDropMap.getOrDefault(entityType, null);
    }

    public static void setGlow(@NotNull SlimefunItemStack i) {
        ItemMeta m = i.getItemMeta();
        if (m != null) {
            m.addEnchant(Enchantment.LURE, 1, true);
            m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            i.setItemMeta(m);
        }
    }

    public static BossBar createBossBar(Player p) {
        BossBar bar = Bukkit.createBossBar("§2Radiation Protection", BarColor.GREEN, BarStyle.SOLID);
        bar.addPlayer(p);
        bar.setProgress(1.0);
        return bar;
    }

    public static void spawnBeam(Player p, Location loc) {
        Location pChest = p.getLocation().add(0, 1.08, 0);
        Vector dir = loc.toVector().subtract(pChest.toVector()).normalize();
        double dist = pChest.distance(loc);
        int count = 30;
        double step = dist / count;

        for (int i = 0; i <= count; i++) {
            Location partLoc = pChest.clone().add(dir.clone().multiply(step * i));
            partLoc.getWorld().spawnParticle(Particle.ITEM_SLIME, partLoc, 1);
        }
    }

    public static boolean isHead(Block b) {
        Material type = b.getType();

        return type == Material.PLAYER_HEAD ||
            type == Material.PLAYER_WALL_HEAD ||
            type == Material.CREEPER_HEAD ||
            type == Material.CREEPER_WALL_HEAD ||
            type == Material.DRAGON_HEAD ||
            type == Material.DRAGON_WALL_HEAD ||
            type == Material.PIGLIN_HEAD ||
            type == Material.PIGLIN_WALL_HEAD ||
            type == Material.ZOMBIE_HEAD ||
            type == Material.ZOMBIE_WALL_HEAD ||
            type == Material.SKELETON_SKULL ||
            type == Material.SKELETON_WALL_SKULL ||
            type == Material.WITHER_SKELETON_SKULL ||
            type == Material.WITHER_SKELETON_WALL_SKULL;
    }

    @Nonnull
    public static ItemStack output(@Nonnull ItemStack itemStack, int amount) {
        ItemStack item = itemStack.clone();
        item.setAmount(amount);
        return item;
    }

}