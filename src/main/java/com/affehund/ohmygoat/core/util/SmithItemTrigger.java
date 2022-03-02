package com.affehund.ohmygoat.core.util;

import com.affehund.ohmygoat.OhMyGoat;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class SmithItemTrigger extends SimpleCriterionTrigger<SmithItemTrigger.TriggerInstance> {
    private static final ResourceLocation ID = new ResourceLocation(OhMyGoat.MOD_ID, "smith_item");

    public ResourceLocation getId() {
        return ID;
    }

    public SmithItemTrigger.TriggerInstance createInstance(@NotNull JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext context) {
        ItemPredicate inputPredicate = ItemPredicate.fromJson(jsonObject.get("input_item"));
        ItemPredicate upgradePredicate = ItemPredicate.fromJson(jsonObject.get("upgrade_item"));
        ItemPredicate resultPredicate = ItemPredicate.fromJson(jsonObject.get("result_item"));
        return new SmithItemTrigger.TriggerInstance(composite, inputPredicate, upgradePredicate, resultPredicate);
    }

    public void trigger(ServerPlayer player, ItemStack inputItem, ItemStack upgradeItem, ItemStack resultItem) {
        this.trigger(player, (consumer) -> consumer.matches(inputItem, upgradeItem, resultItem));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        @Nullable
        private final ItemPredicate inputItem;
        private final ItemPredicate upgradeItem;
        private final ItemPredicate resultItem;

        public TriggerInstance(EntityPredicate.Composite composite, ItemPredicate inputItem, ItemPredicate upgradeItem, ItemPredicate resultItem) {
            super(SmithItemTrigger.ID, composite);
            this.inputItem = inputItem;
            this.upgradeItem = upgradeItem;
            this.resultItem = resultItem;
        }

        public static SmithItemTrigger.TriggerInstance smithItem(ItemPredicate.Builder inputItem, ItemPredicate.Builder upgradeItem, ItemPredicate.Builder resultItem) {
            return new SmithItemTrigger.TriggerInstance(EntityPredicate.Composite.ANY, inputItem.build(), upgradeItem.build(), resultItem.build());
        }

        public boolean matches(ItemStack inputItem, ItemStack upgradeItem, ItemStack resultItem) {
            return this.inputItem.matches(inputItem) && this.upgradeItem.matches(upgradeItem) && this.resultItem.matches(resultItem);
        }

        public JsonObject serializeToJson(SerializationContext context) {
            JsonObject jsonobject = super.serializeToJson(context);
            jsonobject.add("input_item", this.inputItem.serializeToJson());
            jsonobject.add("upgrade_item", this.upgradeItem.serializeToJson());
            jsonobject.add("result_item", this.resultItem.serializeToJson());
            return jsonobject;
        }
    }
}
