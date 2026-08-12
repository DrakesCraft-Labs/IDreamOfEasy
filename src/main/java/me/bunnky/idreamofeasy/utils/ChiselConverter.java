package me.bunnky.idreamofeasy.utils;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;
public class ChiselConverter {
    private static final Map<Material, Material> CHISELED = new HashMap<>();

    static {
        CHISELED.put(Material.ANDESITE, Material.POLISHED_ANDESITE);
        CHISELED.put(Material.POLISHED_ANDESITE, Material.ANDESITE);

        CHISELED.put(Material.BASALT, Material.POLISHED_BASALT);
        CHISELED.put(Material.POLISHED_BASALT, Material.BASALT);

        CHISELED.put(Material.BLACKSTONE, Material.POLISHED_BLACKSTONE);
        CHISELED.put(Material.POLISHED_BLACKSTONE, Material.BLACKSTONE);
        CHISELED.put(Material.CHISELED_POLISHED_BLACKSTONE, Material.POLISHED_BLACKSTONE);
        CHISELED.put(Material.GILDED_BLACKSTONE, Material.POLISHED_BLACKSTONE);

        CHISELED.put(Material.COPPER_BLOCK, Material.CUT_COPPER);
        CHISELED.put(Material.CUT_COPPER, Material.COPPER_BLOCK);
        CHISELED.put(Material.WEATHERED_COPPER, Material.WEATHERED_CUT_COPPER);
        CHISELED.put(Material.WEATHERED_CUT_COPPER, Material.WEATHERED_COPPER);
        CHISELED.put(Material.EXPOSED_COPPER, Material.EXPOSED_CUT_COPPER);
        CHISELED.put(Material.EXPOSED_CUT_COPPER, Material.EXPOSED_COPPER);
        CHISELED.put(Material.OXIDIZED_COPPER, Material.OXIDIZED_CUT_COPPER);
        CHISELED.put(Material.OXIDIZED_CUT_COPPER, Material.OXIDIZED_COPPER);

        CHISELED.put(Material.QUARTZ_BLOCK, Material.QUARTZ_PILLAR);
        CHISELED.put(Material.QUARTZ_PILLAR, Material.CHISELED_QUARTZ_BLOCK);
        CHISELED.put(Material.CHISELED_QUARTZ_BLOCK, Material.QUARTZ_BRICKS);
        CHISELED.put(Material.QUARTZ_BRICKS, Material.QUARTZ_BLOCK);

        CHISELED.put(Material.COBBLED_DEEPSLATE, Material.CHISELED_DEEPSLATE);
        CHISELED.put(Material.CHISELED_DEEPSLATE, Material.POLISHED_DEEPSLATE);
        CHISELED.put(Material.POLISHED_DEEPSLATE, Material.DEEPSLATE_BRICKS);
        CHISELED.put(Material.DEEPSLATE_BRICKS, Material.DEEPSLATE_TILES);
        CHISELED.put(Material.DEEPSLATE_TILES, Material.COBBLED_DEEPSLATE);

        CHISELED.put(Material.COBBLESTONE, Material.MOSSY_COBBLESTONE);
        CHISELED.put(Material.MOSSY_COBBLESTONE, Material.COBBLESTONE);

        CHISELED.put(Material.DIORITE, Material.POLISHED_DIORITE);
        CHISELED.put(Material.POLISHED_DIORITE, Material.DIORITE);

        CHISELED.put(Material.END_STONE, Material.END_STONE_BRICKS);
        CHISELED.put(Material.END_STONE_BRICKS, Material.END_STONE);

        CHISELED.put(Material.GRANITE, Material.POLISHED_GRANITE);
        CHISELED.put(Material.POLISHED_GRANITE, Material.GRANITE);

        CHISELED.put(Material.NETHER_BRICK, Material.CHISELED_NETHER_BRICKS);
        CHISELED.put(Material.CHISELED_NETHER_BRICKS, Material.NETHER_BRICK);

        CHISELED.put(Material.PRISMARINE, Material.PRISMARINE_BRICKS);
        CHISELED.put(Material.PRISMARINE_BRICKS, Material.DARK_PRISMARINE);
        CHISELED.put(Material.DARK_PRISMARINE, Material.PRISMARINE);

        CHISELED.put(Material.PURPUR_BLOCK, Material.PURPUR_PILLAR);
        CHISELED.put(Material.PURPUR_PILLAR, Material.PURPUR_BLOCK);

        CHISELED.put(Material.RED_SANDSTONE, Material.CHISELED_RED_SANDSTONE);
        CHISELED.put(Material.CHISELED_RED_SANDSTONE, Material.SMOOTH_RED_SANDSTONE);
        CHISELED.put(Material.SMOOTH_RED_SANDSTONE, Material.RED_SANDSTONE);

        CHISELED.put(Material.SANDSTONE, Material.CHISELED_SANDSTONE);
        CHISELED.put(Material.CHISELED_SANDSTONE, Material.SMOOTH_SANDSTONE);
        CHISELED.put(Material.SMOOTH_SANDSTONE, Material.SANDSTONE);

        CHISELED.put(Material.STONE, Material.STONE_BRICKS);
        CHISELED.put(Material.STONE_BRICKS, Material.CHISELED_STONE_BRICKS);
        CHISELED.put(Material.CHISELED_STONE_BRICKS, Material.SMOOTH_STONE);
        CHISELED.put(Material.SMOOTH_STONE, Material.STONE);
    }

    public static Material convert(Material input) {
        return CHISELED.getOrDefault(input, input); // Return the input if no conversion found
    }
}