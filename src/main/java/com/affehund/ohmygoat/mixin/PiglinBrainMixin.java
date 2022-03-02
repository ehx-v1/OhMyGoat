package com.affehund.ohmygoat.mixin;

import com.affehund.ohmygoat.core.GoatRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinBrain.class)
public class PiglinBrainMixin {
    @Inject(method = "wearsGoldArmor", at = @At("TAIL"))
    private static void wearsGoldArmor(LivingEntity livingEntity, CallbackInfoReturnable<Boolean> cir) {
        for (ItemStack stack : livingEntity.getArmorItems()) {
            if (stack.getItem().equals(GoatRegistry.HORNED_GOLDEN_HELMET)) {
                cir.setReturnValue(true);
            }
        }
    }
}
