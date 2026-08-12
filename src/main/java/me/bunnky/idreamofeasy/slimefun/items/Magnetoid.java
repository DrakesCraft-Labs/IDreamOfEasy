package me.bunnky.idreamofeasy.slimefun.items;

import com.github.drakescraft_labs.slimefun4.api.items.ItemGroup;
import com.github.drakescraft_labs.slimefun4.api.items.ItemSetting;
import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItem;
import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItemStack;
import com.github.drakescraft_labs.slimefun4.api.items.settings.DoubleRangeSetting;
import com.github.drakescraft_labs.slimefun4.api.recipes.RecipeType;
import com.github.drakescraft_labs.slimefun4.core.attributes.Rechargeable;
import me.bunnky.idreamofeasy.utils.IDOEUtility;
import org.bukkit.inventory.ItemStack;
/*
This device attracts nearby items to itself, making item collection quick and easy. Configurable radius.
 */
public class Magnetoid extends SlimefunItem implements Rechargeable {

    private final ItemSetting<Double> r = new DoubleRangeSetting(this, "pickup-radius", 0.1, 8.0, Double.MAX_VALUE);
    private final float cap;

    public Magnetoid(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, float cap) {
        super(itemGroup, item, recipeType, recipe);
        IDOEUtility.setGlow(item);
        this.cap = cap;

        addItemSetting(r);
    }

    public double getR() {
        return r.getValue();
    }

    @Override
    public float getMaxItemCharge(ItemStack stack) {
        return cap;
    }
}