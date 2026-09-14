package me.bunnky.idreamofeasy.utils;

import org.bukkit.Material;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaterialUtilityTest {

    @Test
    void testGetRandomAmountBounds() {
        MaterialUtility.DropInfo info = new MaterialUtility.DropInfo(Material.COAL, 1, 3);
        boolean sawMin = false;
        boolean sawMax = false;

        for (int i = 0; i < 500; i++) {
            int amount = info.getRandomAmount();
            assertTrue(amount >= 1 && amount <= 3, "Amount must be between 1 and 3 inclusive: " + amount);
            if (amount == 1) sawMin = true;
            if (amount == 3) sawMax = true;
        }

        assertTrue(sawMin, "Expected to see minimum value 1");
        assertTrue(sawMax, "Expected to see maximum value 3 (off-by-one fix)");
    }

    @Test
    void testEqualMinMax() {
        MaterialUtility.DropInfo info = new MaterialUtility.DropInfo(Material.DIAMOND, 1, 1);
        for (int i = 0; i < 50; i++) {
            assertEquals(1, info.getRandomAmount());
        }
    }

    @Test
    void testInvertedMinMax() {
        MaterialUtility.DropInfo info = new MaterialUtility.DropInfo(Material.STONE, 5, 2);
        // Should safely return minAmount without throwing exception
        assertEquals(5, info.getRandomAmount());
    }

    @Test
    void testDropMapContainsNetherOres() {
        assertNotNull(MaterialUtility.getDropInfo(Material.NETHER_QUARTZ_ORE));
        assertNotNull(MaterialUtility.getDropInfo(Material.NETHER_GOLD_ORE));
        assertNotNull(MaterialUtility.getDropInfo(Material.ANCIENT_DEBRIS));
        assertNotNull(MaterialUtility.getDropInfo(Material.GLOWSTONE));
        assertEquals(Material.GLOWSTONE_DUST, MaterialUtility.getDropInfo(Material.GLOWSTONE).getMaterial());
        assertNotNull(MaterialUtility.getDropInfo(Material.AMETHYST_CLUSTER));
        assertEquals(Material.AMETHYST_SHARD, MaterialUtility.getDropInfo(Material.AMETHYST_CLUSTER).getMaterial());
    }
}
