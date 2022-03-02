package com.affehund.ohmygoat.client.layer;

import com.affehund.ohmygoat.OhMyGoat;
import com.affehund.ohmygoat.client.model.HornedHelmetModel;
import com.affehund.ohmygoat.core.util.GoatTags;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HornedHelmetLayer<T extends LivingEntity, M extends EntityModel<T> & HeadedModel> extends RenderLayer<T, M> {
    private static final ResourceLocation HORNED_HELMET_TEXTURE = new ResourceLocation(OhMyGoat.MOD_ID, "textures/entity/horned_helmet.png");
    private final HornedHelmetModel<T> model;

    public HornedHelmetLayer(RenderLayerParent<T, M> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new HornedHelmetModel<>(modelSet.bakeLayer(HornedHelmetModel.HORNED_HELMET_LAYER));
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack itemStack = livingEntity.getItemBySlot(EquipmentSlot.HEAD);
        if (itemStack.is(GoatTags.Items.HORNED_HELMETS)) {
            poseStack.pushPose();
            this.getParentModel().copyPropertiesTo(this.model);
            this.getParentModel().getHead().translateAndRotate(poseStack);
            if (livingEntity.isBaby()) poseStack.translate(0.0D, -0.15D, 0.0D);
            this.model.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            var vertexConsumer = ItemRenderer.getArmorFoilBuffer(multiBufferSource, RenderType.armorCutoutNoCull(HORNED_HELMET_TEXTURE), false, itemStack.hasFoil());
            this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();
        }
    }
}
