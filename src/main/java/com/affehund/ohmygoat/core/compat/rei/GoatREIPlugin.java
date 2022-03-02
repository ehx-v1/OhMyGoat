package com.affehund.ohmygoat.core.compat.rei;

import com.affehund.ohmygoat.OhMyGoat;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public class GoatREIPlugin implements REIServerPlugin {
    public static final CategoryIdentifier CHEESE_MAKING = CategoryIdentifier.of(new Identifier(OhMyGoat.MOD_ID, "cheese_making"));

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(CHEESE_MAKING, CheeseMakingDisplay.serializer());
    }

    public static List<EntryIngredient> toIngredientEntries(ItemStack... stacks) {
        return Arrays.stream(stacks)
                .map(EntryIngredients::of)
                .toList();
    }
}
