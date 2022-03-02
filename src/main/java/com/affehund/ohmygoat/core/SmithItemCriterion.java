package com.affehund.ohmygoat.core;

import com.affehund.ohmygoat.OhMyGoat;
import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class SmithItemCriterion extends AbstractCriterion<SmithItemCriterion.Conditions> {
    private static final Identifier ID = new Identifier(OhMyGoat.MOD_ID, "smith_item");

    @Override
    public Identifier getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player, ItemStack inputItem, ItemStack upgradeItem, ItemStack resultItem) {
        this.trigger(player, (consumer) -> consumer.matches(inputItem, upgradeItem, resultItem));
    }

    @Override
    public Conditions conditionsFromJson(JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        ItemPredicate inputPredicate = ItemPredicate.fromJson(obj.get("input_item"));
        ItemPredicate upgradePredicate = ItemPredicate.fromJson(obj.get("upgrade_item"));
        ItemPredicate resultPredicate = ItemPredicate.fromJson(obj.get("result_item"));
        return new Conditions(playerPredicate, inputPredicate, upgradePredicate, resultPredicate);
    }

    public static class Conditions extends AbstractCriterionConditions {

        @Nullable
        private final ItemPredicate inputItem;
        private final ItemPredicate upgradeItem;
        private final ItemPredicate resultItem;

        public Conditions(EntityPredicate.Extended player, @Nullable ItemPredicate inputItem, ItemPredicate upgradeItem, ItemPredicate resultItem) {
            super(ID, player);
            this.inputItem = inputItem;
            this.upgradeItem = upgradeItem;
            this.resultItem = resultItem;
        }

        @SuppressWarnings("unused")
        public static SmithItemCriterion.Conditions smithItem(ItemPredicate.Builder inputItem, ItemPredicate.Builder upgradeItem, ItemPredicate.Builder resultItem) {
            return new SmithItemCriterion.Conditions(EntityPredicate.Extended.EMPTY, inputItem.build(), upgradeItem.build(), resultItem.build());
        }

        public boolean matches(ItemStack inputItem, ItemStack upgradeItem, ItemStack resultItem) {
            return this.inputItem.test(inputItem) && this.upgradeItem.test(upgradeItem) && this.resultItem.test(resultItem);
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject jsonObject = super.toJson(predicateSerializer);
            jsonObject.add("input_item", this.inputItem.toJson());
            jsonObject.add("upgrade_item", this.upgradeItem.toJson());
            jsonObject.add("result_item", this.resultItem.toJson());
            return jsonObject;
        }
    }
}
