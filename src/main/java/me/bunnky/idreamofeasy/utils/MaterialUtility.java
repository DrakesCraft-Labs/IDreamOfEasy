package me.bunnky.idreamofeasy.utils;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MaterialUtility {

    public static class DropInfo {
        private final Material material;
        private final int minAmount;
        private final int maxAmount;

        public DropInfo(Material material, int minAmount, int maxAmount) {
            this.material = material;
            this.minAmount = minAmount;
            this.maxAmount = maxAmount;
        }

        public Material getMaterial() {
            return material;
        }

        public int getMinAmount() {
            return minAmount;
        }

        public int getMaxAmount() {
            return maxAmount;
        }

        public int getRandomAmount() {
            if (minAmount == maxAmount) {
                return minAmount;
            }
            Random random = ThreadLocalRandom.current();
            return random.nextInt(minAmount, maxAmount);
        }

    }

    private static final Map<Material, DropInfo> DROP_MAP = createDropMap();

    private static Map<Material, DropInfo> createDropMap() {
        Map<Material, DropInfo> dropMap = new HashMap<>();

        dropMap.put(Material.STONE, new DropInfo(Material.COBBLESTONE, 1, 1));
        dropMap.put(Material.GRASS_BLOCK, new DropInfo(Material.DIRT, 1, 1));
        dropMap.put(Material.DEEPSLATE, new DropInfo(Material.COBBLED_DEEPSLATE, 1, 1));

        dropMap.put(Material.COAL_ORE, new DropInfo(Material.COAL, 1, 3));
        dropMap.put(Material.DEEPSLATE_COAL_ORE, new DropInfo(Material.COAL, 1, 3));
        dropMap.put(Material.IRON_ORE, new DropInfo(Material.RAW_IRON, 1, 1));
        dropMap.put(Material.DEEPSLATE_IRON_ORE, new DropInfo(Material.RAW_IRON, 1, 1));
        dropMap.put(Material.GOLD_ORE, new DropInfo(Material.RAW_GOLD, 1, 1));
        dropMap.put(Material.DEEPSLATE_GOLD_ORE, new DropInfo(Material.RAW_GOLD, 1, 1));
        dropMap.put(Material.COPPER_ORE, new DropInfo(Material.RAW_COPPER, 1, 1));
        dropMap.put(Material.DEEPSLATE_COPPER_ORE, new DropInfo(Material.RAW_COPPER, 1, 1));
        dropMap.put(Material.DIAMOND_ORE, new DropInfo(Material.DIAMOND, 1, 1));
        dropMap.put(Material.DEEPSLATE_DIAMOND_ORE, new DropInfo(Material.DIAMOND, 1, 1));
        dropMap.put(Material.EMERALD_ORE, new DropInfo(Material.EMERALD, 1, 1));
        dropMap.put(Material.DEEPSLATE_EMERALD_ORE, new DropInfo(Material.EMERALD, 1, 1));
        dropMap.put(Material.REDSTONE_ORE, new DropInfo(Material.REDSTONE, 1, 5));
        dropMap.put(Material.DEEPSLATE_REDSTONE_ORE, new DropInfo(Material.REDSTONE, 1, 5));
        dropMap.put(Material.LAPIS_ORE, new DropInfo(Material.LAPIS_LAZULI, 4, 6));
        dropMap.put(Material.DEEPSLATE_LAPIS_ORE, new DropInfo(Material.LAPIS_LAZULI, 4, 6));

        dropMap.put(Material.NETHER_QUARTZ_ORE, new DropInfo(Material.QUARTZ, 1, 1));
        dropMap.put(Material.NETHER_GOLD_ORE, new DropInfo(Material.GOLD_NUGGET, 2, 6));
        dropMap.put(Material.ANCIENT_DEBRIS, new DropInfo(Material.ANCIENT_DEBRIS, 1, 1));

        return dropMap;
    }

    public static DropInfo getDropInfo(Material material) {
        return DROP_MAP.getOrDefault(material, new DropInfo(material, 1, 1));
    }
}

