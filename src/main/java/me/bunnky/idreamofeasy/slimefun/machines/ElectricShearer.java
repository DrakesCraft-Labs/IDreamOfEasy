package me.bunnky.idreamofeasy.slimefun.machines;

import com.github.drakescraft_labs.slimefun4.api.events.PlayerRightClickEvent;
import com.github.drakescraft_labs.slimefun4.api.items.ItemGroup;
import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItem;
import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItemStack;
import com.github.drakescraft_labs.slimefun4.api.recipes.RecipeType;
import com.github.drakescraft_labs.slimefun4.core.attributes.EnergyNetComponent;
import com.github.drakescraft_labs.slimefun4.core.handlers.BlockUseHandler;
import com.github.drakescraft_labs.slimefun4.core.networks.energy.EnergyNetComponentType;
import me.bunnky.idreamofeasy.IDreamOfEasy;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import com.github.drakescraft_labs.slimefun4.legacy.Objects.handlers.BlockTicker;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class ElectricShearer extends SlimefunItem implements EnergyNetComponent {
    private final int cap;
    private final int ecost;

    public ElectricShearer(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int ecost, int cap, int range) {
        super(itemGroup, item, recipeType, recipe);
        this.cap = cap;
        this.ecost = ecost;

        addItemHandler(onRightClick());

        addItemHandler(new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return false;
            }

            @Override
            public void tick(Block b, SlimefunItem sfItem, Config config) {
                shearSheep(b.getLocation(), range);
            }
        });
    }


    private void shearSheep(Location loc, int range) {
        Bukkit.getScheduler().runTask(IDreamOfEasy.getInstance(), () -> {
            for (Entity entity : loc.getNearbyEntities(range, range, range)) {
                if (entity.getType() == EntityType.SHEEP) {
                    if (entity.getLocation().distance(loc) <= range) {
                        Sheep sheep = (Sheep) entity;
                        if (!sheep.isSheared()) {
                            sheep.setSheared(true);

                            Material woolMaterial = Material.valueOf(sheep.getColor().name() + "_WOOL");
                            ItemStack wool = new ItemStack(woolMaterial, 1);

                            entity.getWorld().dropItemNaturally(entity.getLocation(), wool);

                            Location sheepLocation = sheep.getLocation();
                            sheep.getWorld().spawnParticle(Particle.CLOUD, sheepLocation, 20, 0.5, 0.5, 0.5, 0.1);
                            sheep.getWorld().playSound(sheepLocation, Sound.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
                        }
                    }
                }
            }
        });
    }

    public @Nonnull BlockUseHandler onRightClick() {
        return PlayerRightClickEvent::cancel;
    }

    @Override
    public @NotNull EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return this.cap;
    }
}