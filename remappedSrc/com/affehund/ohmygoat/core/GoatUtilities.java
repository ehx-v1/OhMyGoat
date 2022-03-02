package com.affehund.ohmygoat.core;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import I;
import java.util.ArrayList;
import java.util.Random;

public class GoatUtilities {
    public static boolean cureBadEffects(LivingEntity livingEntity) {
        var badEffects = new ArrayList<StatusEffect>();
        livingEntity.getStatusEffects().stream().map(StatusEffectInstance::getEffectType)
                .filter(e -> e.getCategory().equals(StatusEffectCategory.HARMFUL))
                .forEach(badEffects::add);
        var count = badEffects.size();
        badEffects.forEach(livingEntity::removeStatusEffect);
        return count > 0;
    }

    public static boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    public static boolean hasEnchantment(ItemStack itemstack, Enchantment enchantment) {
        return EnchantmentHelper.getLevel(enchantment, itemstack) != 0;
    }

    public static int randomInRange(@NotNull Random random, int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}
