package me.bunnky.idreamofeasy.slimefun.items;

import com.github.drakescraft_labs.slimefun4.api.items.ItemGroup;
import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItemStack;
import com.github.drakescraft_labs.slimefun4.api.player.PlayerProfile;
import com.github.drakescraft_labs.slimefun4.api.recipes.RecipeType;
import com.github.drakescraft_labs.slimefun4.api.researches.Research;
import com.github.drakescraft_labs.slimefun4.core.handlers.ItemUseHandler;
import com.github.drakescraft_labs.slimefun4.implementation.Slimefun;
import com.github.drakescraft_labs.slimefun4.implementation.items.SimpleSlimefunItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TomeOfEnlightenment extends SimpleSlimefunItem<ItemUseHandler> {

    private final Random random = new Random();

    public TomeOfEnlightenment(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public @NotNull ItemUseHandler getItemHandler() {
        return event -> {
            Player player = event.getPlayer();

            PlayerProfile.get(player, profile -> {
                List<Research> unlockableResearches = getAffordableResearch(player, profile);

                if (unlockableResearches.isEmpty()) {
                    player.sendMessage("§c经验不足或你已解锁所有研究。");
                    return;
                }

                Research randomResearch = unlockableResearches.get(random.nextInt(unlockableResearches.size()));
                int cost = randomResearch.getCost();

                if (profile.hasUnlocked(randomResearch)) {
                    return;
                }

                if (player.getLevel() >= cost) {
                    player.setLevel(player.getLevel() - cost);
                    randomResearch.unlock(player, true);
                }
            });
        };
    }

    private List<Research> getAffordableResearch(Player player, PlayerProfile profile) {
        List<Research> affordableResearches = new ArrayList<>();
        for (Research research : Slimefun.getRegistry().getResearches()) {
            if (!research.isEnabled() || research.getCost() > player.getLevel()) {
                continue;
            }
            if (!research.getAffectedItems().isEmpty() && research.canUnlock(player) &&
                !profile.hasUnlocked(research)) {
                affordableResearches.add(research);
            }
        }
        return affordableResearches;
    }
}