package com.affehund.ohmygoat.core.compat.rei.client;

import com.affehund.ohmygoat.core.compat.rei.CheeseMakingDisplay;
import com.affehund.ohmygoat.core.compat.rei.GoatREIPlugin;
import com.google.common.collect.Lists;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.text.DecimalFormat;
import java.util.List;

public class CheeseMakingCategory implements DisplayCategory<Display> {

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(Items.CAULDRON);
    }

    @Override
    public Text getTitle() {
        return new TranslatableText("category.ohmygoat.cheese_making");
    }

    @Override
    public CategoryIdentifier<? extends Display> getCategoryIdentifier() {
        return GoatREIPlugin.CHEESE_MAKING;
    }

    @Override
    public List<Widget> setupDisplay(Display display, Rectangle bounds) {
        var startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 13);
        var cookingTime = CheeseMakingDisplay.getCookingTime();
        var df = new DecimalFormat("###.##");
        List<Widget> widgets = Lists.newArrayList();

        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61, startPoint.y + 5)));
        widgets.add(Widgets.createLabel(new Point(bounds.x + bounds.width - 5, bounds.y + 5), new TranslatableText("category.rei.campfire.time", df.format(cookingTime / 20d))).noShadow().rightAligned().color(0xFF404040, 0xFFBBBBBB));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 24, startPoint.y + 8)).animationDurationTicks(cookingTime));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y + 5)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 4, startPoint.y + 5)).entries(display.getInputEntries().get(0)).markInput());
        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 49;
    }
}
