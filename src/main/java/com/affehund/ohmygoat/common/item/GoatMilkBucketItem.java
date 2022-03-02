package com.affehund.ohmygoat.common.item;

import com.affehund.ohmygoat.core.util.GoatUtilities;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class GoatMilkBucketItem extends MilkBucketItem {
    public GoatMilkBucketItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, Level level, @NotNull LivingEntity livingEntity) {
        if (!level.isClientSide) GoatUtilities.cureBadEffects(livingEntity);

        if (livingEntity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }

        if (livingEntity instanceof Player player && !player.getAbilities().instabuild) stack.shrink(1);

        return stack.isEmpty() ? new ItemStack(Items.BUCKET) : stack;
    }
}
