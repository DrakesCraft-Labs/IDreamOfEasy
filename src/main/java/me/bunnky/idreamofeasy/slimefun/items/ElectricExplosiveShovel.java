package me.bunnky.idreamofeasy.slimefun.items;

import com.github.drakescraft_labs.slimefun4.api.events.PlayerRightClickEvent;
import com.github.drakescraft_labs.slimefun4.api.items.ItemGroup;
import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItemStack;
import com.github.drakescraft_labs.slimefun4.api.recipes.RecipeType;
import com.github.drakescraft_labs.slimefun4.core.attributes.Rechargeable;
import com.github.drakescraft_labs.slimefun4.core.handlers.ItemUseHandler;
import com.github.drakescraft_labs.slimefun4.core.handlers.ToolUseHandler;
import com.github.drakescraft_labs.slimefun4.implementation.items.tools.ExplosiveShovel;
import me.bunnky.idreamofeasy.utils.IDOEUtility;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


public class ElectricExplosiveShovel extends ExplosiveShovel implements Rechargeable {

    private final float cap;
    private static final float COST = 0.3F;

    public ElectricExplosiveShovel(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, float cap) {
        super(itemGroup, item, recipeType, recipe);
        IDOEUtility.setGlow(item);
        this.cap = cap;
        addItemHandler(onRightClick());
    }

    public ItemUseHandler onRightClick() {
        return PlayerRightClickEvent::cancel;
    }

    @Override
    public @NotNull ToolUseHandler getItemHandler() {
        return (e, tool, fortune, drops) -> {
            Player p = e.getPlayer();
            ItemStack itemInHand = p.getInventory().getItemInMainHand();

            if (!removeItemCharge(itemInHand, COST)) {
                e.setCancelled(true);
                return;
            }

            super.getItemHandler().onToolUse(e, tool, fortune, drops);
        };
    }

    @Override
    public float getMaxItemCharge(ItemStack itemStack) {
        return cap;
    }

    @Override
    protected boolean canBreak(@NotNull Player p, @NotNull Block b) {
        ItemStack itemInHand = p.getInventory().getItemInMainHand();
        return removeItemCharge(itemInHand, COST) && super.canBreak(p, b);
    }
}