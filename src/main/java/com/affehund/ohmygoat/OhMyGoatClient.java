package com.affehund.ohmygoat;

import com.affehund.ohmygoat.client.layer.HornedHelmetLayer;
import com.affehund.ohmygoat.client.model.HornedHelmetModel;
import com.affehund.ohmygoat.core.GoatRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.ArmorStandEntityRenderer;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.item.DyeableArmorItem;

@Environment(EnvType.CLIENT)
public class OhMyGoatClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(HornedHelmetModel.HORNED_HELMET_LAYER, HornedHelmetModel::getTexturedModelData);

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityRenderer instanceof BipedEntityRenderer || entityRenderer instanceof ArmorStandEntityRenderer) {
                registrationHelper.register(new HornedHelmetLayer(entityRenderer, context.getModelLoader()));
            } else if (entityRenderer instanceof PlayerEntityRenderer playerEntityRenderer) {
                registrationHelper.register(new HornedHelmetLayer<>(playerEntityRenderer, context.getModelLoader()));
            }
        });

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex != 0 ? -1 : ((DyeableArmorItem) stack.getItem()).getColor(stack), GoatRegistry.HORNED_LEATHER_HELMET);
    }
}
