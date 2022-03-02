package com.affehund.ohmygoat.core.compat.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.extensions.IRecipeCategoryExtension;
import net.minecraft.world.item.ItemStack;

public record CheeseMakingWrapper(ItemStack input, ItemStack result,
                                  int cookingTime) implements IRecipeCategoryExtension {

    @Override
    public void setIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, this.input);
        ingredients.setOutput(VanillaTypes.ITEM, this.result);
    }

    public ItemStack getInput() {
        return this.input;
    }

    public ItemStack getResult() {
        return this.result;
    }

    public int getCookingTime() {
        return this.cookingTime;
    }
}
