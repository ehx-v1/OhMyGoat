package com.affehund.ohmygoat.core.compat.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;

public class CheeseMakingDisplay extends BasicDisplay {
    private static int cookingTime;

    public CheeseMakingDisplay(ItemStack input, ItemStack result,
                               int cookingTime) {
        super(GoatREIPlugin.toIngredientEntries(input), GoatREIPlugin.toIngredientEntries(result));
        CheeseMakingDisplay.cookingTime = cookingTime;
    }

    public CheeseMakingDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<Identifier> location) {
        super(inputs, outputs, location);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return GoatREIPlugin.CHEESE_MAKING;
    }

    public static Serializer<CheeseMakingDisplay> serializer() {
        return Serializer.ofSimple(CheeseMakingDisplay::new);
    }

    public static int getCookingTime() {
        return cookingTime;
    }
}
