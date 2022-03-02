package com.affehund.ohmygoat.core.util;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class GoatUtilities {
    public static boolean cureBadEffects(LivingEntity livingEntity) {
        var badEffects = new ArrayList<MobEffect>();
        livingEntity.getActiveEffects().stream().map(MobEffectInstance::getEffect)
                .filter(e -> e.getCategory().equals(MobEffectCategory.HARMFUL))
                .forEach(badEffects::add);
        var count = badEffects.size();
        badEffects.forEach(livingEntity::removeEffect);
        return count > 0;
    }

    public static boolean hasEnchantment(ItemStack itemstack, Enchantment enchantment) {
        return EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack) != 0;
    }

    public static boolean isModLoaded(String modID) {
        return ModList.get() != null && ModList.get().getModContainerById(modID).isPresent();
    }

    public static int randomInRange(@NotNull Random random, int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}
