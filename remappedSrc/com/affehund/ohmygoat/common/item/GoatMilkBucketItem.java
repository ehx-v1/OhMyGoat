package com.affehund.ohmygoat.common.item;

import com.affehund.ohmygoat.core.GoatUtilities;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

public class GoatMilkBucketItem extends MilkBucketItem {
    public GoatMilkBucketItem(Settings settings) {
        super(settings);
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity livingEntity) {
        if (!world.isClient) GoatUtilities.cureBadEffects(livingEntity);

        if (livingEntity instanceof ServerPlayerEntity serverPlayer) {
            Criteria.CONSUME_ITEM.trigger(serverPlayer, stack);
            serverPlayer.incrementStat(Stats.USED.getOrCreateStat(this));
        }

        if (livingEntity instanceof PlayerEntity player && !player.getAbilities().creativeMode) stack.decrement(1);

        return stack.isEmpty() ? new ItemStack(Items.BUCKET) : stack;
    }
}
