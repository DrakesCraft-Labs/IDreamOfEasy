package me.bunnky.idreamofeasy.slimefun.machines;

import com.github.drakescraft_labs.slimefun4.api.items.ItemGroup;
import com.github.drakescraft_labs.slimefun4.api.items.ItemSetting;
import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItem;
import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItemStack;
import com.github.drakescraft_labs.slimefun4.api.recipes.RecipeType;
import com.github.drakescraft_labs.slimefun4.core.attributes.EnergyNetComponent;
import com.github.drakescraft_labs.slimefun4.core.networks.energy.EnergyNetComponentType;
import com.github.drakescraft_labs.slimefun4.core.services.sounds.SoundEffect;
import com.github.drakescraft_labs.slimefun4.implementation.Slimefun;
import com.github.drakescraft_labs.slimefun4.implementation.handlers.VanillaInventoryDropHandler;
import com.github.drakescraft_labs.slimefun4.implementation.items.SimpleSlimefunItem;
import com.github.drakescraft_labs.slimefun4.libraries.dough.protection.Interaction;
import me.bunnky.idreamofeasy.utils.IDOEUtility;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import com.github.drakescraft_labs.slimefun4.legacy.Objects.handlers.BlockTicker;
import com.github.drakescraft_labs.slimefun4.legacy.api.BlockStorage;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Hopper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
/*
A convenient pickup point for players to collect items easily.
 */
public class SupplyHopper extends SimpleSlimefunItem<BlockTicker> implements EnergyNetComponent {

    private final ItemSetting<Boolean> silent = new ItemSetting<>(this, "silent", false);
    private final ItemSetting<Boolean> toggleable = new ItemSetting<>(this, "toggleable-with-redstone", false);

    private final int cap;
    private final int ecost;

    public SupplyHopper(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int ecost, int cap) {
        super(itemGroup, item, recipeType, recipe);
        IDOEUtility.setGlow(item);

        this.cap = cap;
        this.ecost = ecost;

        addItemSetting(silent, toggleable);
        addItemHandler(new VanillaInventoryDropHandler<>(org.bukkit.block.Hopper.class));
    }

    @Override
    public @NotNull BlockTicker getItemHandler() {
        return new BlockTicker() {
            @Override
            public void tick(Block b, SlimefunItem sfItem, Config config) {
                if (b.getType() != Material.HOPPER) {
                    BlockStorage.clearBlockInfo(b);
                    return;
                }

                if (toggleable.getValue()) {
                    Hopper hopper = (Hopper) b.getBlockData();
                    if (!hopper.isEnabled()) {
                        return;
                    }
                }

                Location loc = b.getLocation();
                boolean playSound = false;
                double xMin = -1;
                double xMax = 1;
                double yMin = -1.0;
                double yMax = 0;
                double zMin = -1;
                double zMax = 1;

                List<Player> players = (List<Player>) loc.getNearbyPlayers(xMax - xMin, yMax - yMin, zMax - zMin);

                for (Player p : players) {
                    if (!(Slimefun.getProtectionManager().hasPermission(p, b, Interaction.INTERACT_BLOCK)) || p.getGameMode() == GameMode.SPECTATOR) {
                        continue;
                    }

                    Location ploc = p.getLocation();
                    double blockCenterX = loc.getX() + 0.5;
                    double blockCenterY = loc.getY() - 1.7;
                    double blockCenterZ = loc.getZ() + 0.5;


                    if (ploc.getX() >= blockCenterX + xMin && ploc.getX() <= blockCenterX + xMax &&
                        ploc.getY() >= blockCenterY + yMin && ploc.getY() <= blockCenterY + yMax &&
                        ploc.getZ() >= blockCenterZ + zMin && ploc.getZ() <= blockCenterZ + zMax) {

                        org.bukkit.block.Hopper h = (org.bukkit.block.Hopper) b.getState();
                        ItemStack[] hItems = h.getInventory().getContents();

                        if (!(h.getInventory().getViewers().isEmpty())){
                            return;
                        }

                        for (ItemStack item : hItems) {
                            if (item != null && getCharge(loc) >= ecost) {
                                int totalAmountToAdd = item.getAmount();
                                HashMap<Integer, ItemStack> leftover = p.getInventory().addItem(item.clone());

                                if (leftover.isEmpty()) {
                                    item.setAmount(0);
                                    removeCharge(loc, ecost);
                                    b.getWorld().spawnParticle(Particle.CRIT, b.getLocation().add(0.5, 0, 0.5), 10, 0.3, 0.3, 0.3, 0.05);
                                    playSound = true;
                                } else {
                                    int excess = leftover.values().stream().mapToInt(ItemStack::getAmount).sum();
                                    int toAdd = totalAmountToAdd - excess;

                                    if (toAdd > 0) {
                                        item.setAmount(item.getAmount() - toAdd);
                                    }

                                    if (item.getAmount() <= 0) {
                                        h.getInventory().remove(item);
                                    }
                                }
                            }
                        }
                    }
                }

                if (playSound && !silent.getValue()) {
                    SoundEffect.INFUSED_HOPPER_TELEPORT_SOUND.playAt(b);
                }
            }

            @Override
            public boolean isSynchronized() {
                return true;
            }
        };
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