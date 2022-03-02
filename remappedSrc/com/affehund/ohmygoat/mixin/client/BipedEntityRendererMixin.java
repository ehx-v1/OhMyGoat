package com.affehund.ohmygoat.mixin.client;

import com.affehund.ohmygoat.client.layer.HornedHelmetLayer;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityRenderer.class)
public abstract class BipedEntityRendererMixin<T extends MobEntity, M extends BipedEntityModel<T>> extends MobEntityRenderer<T, M> {
    
    public BipedEntityRendererMixin(EntityRendererFactory.Context ctx, M entityModel, float shadowRadius) {
        super(ctx, entityModel, shadowRadius);
    }

    @Inject(method = "<init>*", at = @At("TAIL"))
    public void init(EntityRendererFactory.Context ctx, M model, float shadowRadius, CallbackInfo ci) {
        this.addFeature(new HornedHelmetLayer<>(this, ctx.getModelLoader()));
    }
}
