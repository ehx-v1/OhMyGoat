package com.affehund.ohmygoat.mixin;

import com.affehund.ohmygoat.core.GoatRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinAi.class)
public class PiglinAiMixin {

    @Inject(method = "isWearingGold(Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At("TAIL"))
    private static void isWearingGold(LivingEntity livingEntity, CallbackInfoReturnable<Boolean> cir) {
        for (ItemStack stack : livingEntity.getArmorSlots()) {
            if (stack.getItem().equals(GoatRegistry.HORNED_GOLDEN_HELMET.get())) {
                cir.setReturnValue(true);
            }
        }
    }
}
