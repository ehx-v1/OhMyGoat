package com.affehund.ohmygoat.mixin;

import com.affehund.ohmygoat.core.GoatRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GoatEntity.class)
public abstract class GoatMixin extends AnimalEntity {
    protected GoatMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "interactMob(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;", at = @At("HEAD"), cancellable = true)
    private void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack handStack = player.getStackInHand(hand);
        if (handStack.isOf(Items.BUCKET) && !this.isBaby()) {
            player.playSound(this.getMilkingSound(), 1.0F, 1.0F);
            ItemStack goatMilkBucket = ItemUsage.exchangeStack(handStack, player, GoatRegistry.GOAT_MILK_BUCKET.getDefaultStack());
            player.setStackInHand(hand, goatMilkBucket);
            cir.setReturnValue(ActionResult.success(this.world.isClient));
        } else {
            ActionResult interactionResult = super.interactMob(player, hand);
            if (interactionResult.isAccepted() && this.isBreedingItem(handStack)) {
                this.world.playSound(null, this.getBlockPos(), this.getEatSound(handStack), SoundCategory.NEUTRAL, 1.0F, MathHelper.nextBetween(this.world.random, 0.8F, 1.2F));
            }
            cir.setReturnValue(interactionResult);
        }
    }

    @Shadow
    protected abstract SoundEvent getMilkingSound();
}
