package com.affehund.ohmygoat.core.compat.jei;

import com.affehund.ohmygoat.OhMyGoat;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class CheeseMakingCategory implements IRecipeCategory<CheeseMakingWrapper> {
    public static final ResourceLocation ID = new ResourceLocation(OhMyGoat.MOD_ID, "cheese_making");
    public static final ResourceLocation TEXTURE = new ResourceLocation(OhMyGoat.MOD_ID, "textures/gui/cheese_making.png");

    private final IDrawable icon;
    private final IDrawableStatic background;
    private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;

    public CheeseMakingCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 81, 40);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(Items.CAULDRON));
        this.cachedArrows = CacheBuilder.newBuilder().maximumSize(25).build(new CacheLoader<>() {
            @Override
            public @NotNull IDrawableAnimated load(@NotNull Integer cookingTime) {
                return guiHelper.drawableBuilder(TEXTURE, 82, 0, 24, 17)
                        .buildAnimated(cookingTime, IDrawableAnimated.StartDirection.LEFT, false);
            }
        });
    }

    @Override
    public @NotNull ResourceLocation getUid() {
        return ID;
    }

    @Override
    public @NotNull Class<? extends CheeseMakingWrapper> getRecipeClass() {
        return CheeseMakingWrapper.class;
    }

    @Override
    public @NotNull Component getTitle() {
        return new TranslatableComponent("category.ohmygoat.cheese_making");
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setIngredients(CheeseMakingWrapper recipe, IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, recipe.getInput());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResult());
    }

    @Override
    public void draw(@NotNull CheeseMakingWrapper recipe, @NotNull PoseStack poseStack, double x, double y) {
        this.getArrow(recipe).draw(poseStack, 24, 5);
        this.drawCookTime(recipe, poseStack);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, @NotNull CheeseMakingWrapper recipe, @NotNull IIngredients ingredients) {
        var stacks = recipeLayout.getItemStacks();

        stacks.init(0, true, 0, 4);
        stacks.init(1, false, 60, 4);

        stacks.set(ingredients);
    }

    private IDrawableAnimated getArrow(CheeseMakingWrapper recipe) {
        int cookingTime = recipe.getCookingTime();
        if (cookingTime <= 0) {
            cookingTime = 600;
        }
        return this.cachedArrows.getUnchecked(cookingTime);
    }

    private void drawCookTime(CheeseMakingWrapper recipe, PoseStack poseStack) {
        int cookTime = recipe.getCookingTime();
        if (cookTime > 0) {
            int cookTimeSeconds = cookTime / 20;
            TranslatableComponent timeString = new TranslatableComponent("gui.jei.category.smelting.time.seconds", cookTimeSeconds);
            Minecraft minecraft = Minecraft.getInstance();
            Font fontRenderer = minecraft.font;
            int stringWidth = fontRenderer.width(timeString);
            fontRenderer.draw(poseStack, timeString, background.getWidth() - stringWidth, 31, 0xFF808080);
        }
    }
}
