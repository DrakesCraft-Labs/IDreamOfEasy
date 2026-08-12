package me.bunnky.idreamofeasy.slimefun.machines.multiblock;

import com.github.drakescraft_labs.slimefun4.api.items.ItemGroup;
import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItemStack;
import com.github.drakescraft_labs.slimefun4.implementation.SlimefunItems;
import com.github.drakescraft_labs.slimefun4.implementation.items.multiblocks.miner.IndustrialMiner;
import com.github.drakescraft_labs.slimefun4.legacy.Objects.SlimefunItem.abstractItems.MachineFuel;
import com.github.drakescraft_labs.slimefun4.legacy.api.BlockStorage;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
/*
Description: The Elite Terrabore is a variant of the Terrabore. It works in a 21x21 radius and mines everything. Requires uranium as a fuel source
 */
public class EliteTerrabore extends IndustrialMiner {
    public EliteTerrabore(ItemGroup itemGroup, SlimefunItemStack item) {
        super(itemGroup, item, Material.NETHERITE_BLOCK, true, 10);
    }

    @Override
    protected void registerDefaultFuelTypes() {
        fuelTypes.add(new MachineFuel(640, SlimefunItems.TINY_URANIUM));
        fuelTypes.add(new MachineFuel(1280, SlimefunItems.SMALL_URANIUM));
        fuelTypes.add(new MachineFuel(1920, SlimefunItems.URANIUM));
        fuelTypes.add(new MachineFuel(3840, SlimefunItems.BOOSTED_URANIUM));
    }

    @Override
    public @NotNull ItemStack getOutcome(@NotNull Material material) {
        return new ItemStack(material);
    }

    @Override
    public boolean canMine(@NotNull Block b) {
        return b.getType().getHardness() >= 0 &&
            b.getType().isSolid() &&
            !BlockStorage.hasBlockInfo(b);
    }
}
