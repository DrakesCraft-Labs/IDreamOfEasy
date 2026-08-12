package me.bunnky.idreamofeasy.slimefun.items;

import com.github.drakescraft_labs.slimefun4.api.items.ItemGroup;
import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItem;
import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItemStack;
import com.github.drakescraft_labs.slimefun4.api.recipes.RecipeType;
import com.github.drakescraft_labs.slimefun4.core.handlers.BlockBreakHandler;
import com.github.drakescraft_labs.slimefun4.core.handlers.BlockPlaceHandler;
import me.bunnky.idreamofeasy.utils.IDOEUtility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
/*
A crafted block that players can place and break to obtain a Trim item. Includes Netherite Smithing template.
 */


public class TrimVault extends SlimefunItem  {

    public TrimVault(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        IDOEUtility.setGlow(item);


        addItemHandler(new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@NotNull BlockPlaceEvent e) {
                Location placedLocation = e.getBlock().getLocation();

                World w = placedLocation.getWorld();

                if (w != null) {
                    w.spawnParticle(Particle.EGG_CRACK, placedLocation.add(0.5, 0.5, 0.5), 30, 0.3, 0.3, 0.3, 0.05);
                }
            }
        });
        addItemHandler(new BlockBreakHandler(false, true) {
            @Override
            public void onPlayerBreak(@NotNull BlockBreakEvent e, @NotNull ItemStack stack, @NotNull List<ItemStack> list) {

                Player p = e.getPlayer();
                ItemStack randTemp = IDOEUtility.getRandomTemplate();

                if (randTemp != null) {
                    p.getWorld().dropItem(e.getBlock().getLocation(), randTemp);
                }
            }
        });
    }

    @Override
    public @NotNull Collection<ItemStack> getDrops() {
        return List.of(new ItemStack(Material.AIR));
    }
}
