package me.bunnky.idreamofeasy.slimefun.items;

import com.github.drakescraft_labs.slimefun4.api.events.PlayerRightClickEvent;
import com.github.drakescraft_labs.slimefun4.api.items.ItemGroup;
import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItem;
import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItemStack;
import com.github.drakescraft_labs.slimefun4.api.recipes.RecipeType;
import com.github.drakescraft_labs.slimefun4.core.handlers.ItemUseHandler;
import com.github.drakescraft_labs.slimefun4.implementation.Slimefun;
import com.github.drakescraft_labs.slimefun4.implementation.items.SimpleSlimefunItem;
import com.github.drakescraft_labs.slimefun4.libraries.dough.protection.Interaction;
import me.bunnky.idreamofeasy.utils.IDOEUtility;
import com.github.drakescraft_labs.slimefun4.legacy.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/*
This specialized tool allows you to break heads with a simple right-click, clearing them from your path with ease.
 */

public class Jawn extends SimpleSlimefunItem<ItemUseHandler> {

    public Jawn(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        IDOEUtility.setGlow(item);
    }

    @Nonnull
    @Override
    public ItemUseHandler getItemHandler() {
        return this::onRightClick;
    }

    private void onRightClick(@NotNull PlayerRightClickEvent e) {
        SlimefunItem hand = SlimefunItem.getByItem(e.getItem());

        if (!(hand instanceof Jawn)) return;

        e.cancel();

        Player p = e.getPlayer();
        Block b = e.getInteractEvent().getClickedBlock();

        if (b == null) return;

        if (IDOEUtility.isHead(b)) breakBlock(b, p);
    }

    public static void breakBlock(Block b, Player p) {
        if (!(Slimefun.getProtectionManager().hasPermission(p, b, Interaction.INTERACT_BLOCK))){
            return;
        }
        BlockBreakEvent breakEvent = new BlockBreakEvent(b, p);
        Bukkit.getPluginManager().callEvent(breakEvent);
        if (!breakEvent.isCancelled()) {
            if (BlockStorage.hasBlockInfo(b)) {
                BlockStorage.clearBlockInfo(b);
                b.setType(Material.AIR);
            } else {
                b.breakNaturally();
            }
        }
    }

}