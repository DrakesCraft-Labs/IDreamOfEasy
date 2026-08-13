package me.bunnky.idreamofeasy.slimefun.machines.repellers;

import com.github.drakescraft_labs.slimefun4.api.items.ItemGroup;
import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItemStack;
import com.github.drakescraft_labs.slimefun4.api.recipes.RecipeType;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class ZombieVillagerRepeller extends MobRepeller {

    public ZombieVillagerRepeller(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int ecost, int cap) {
        super(itemGroup, item, recipeType, recipe, ecost, cap);
    }

    @Override
    protected EntityType getRepelledEntityType() {
        return EntityType.ZOMBIE_VILLAGER;
    }

    @Override
    protected String getRepelledEntityName() {
        return "Aldeano Zombi";
    }
}
