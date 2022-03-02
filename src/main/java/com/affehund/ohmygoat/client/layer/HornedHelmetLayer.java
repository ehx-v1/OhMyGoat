package com.affehund.ohmygoat.client.layer;

import com.affehund.ohmygoat.OhMyGoat;
import com.affehund.ohmygoat.client.model.HornedHelmetModel;
import com.affehund.ohmygoat.core.GoatTags;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class HornedHelmetLayer<T extends LivingEntity, M extends EntityModel<T> & ModelWithHead> extends FeatureRenderer<T, M> {
    private static final Identifier HORNED_HELMET_TEXTURE = new Identifier(OhMyGoat.MOD_ID, "textures/entity/horned_helmet.png");
    private final HornedHelmetModel<T> model;

    public HornedHelmetLayer(FeatureRendererContext<T, M> renderer, EntityModelLoader modelSet) {
        super(renderer);
        this.model = new HornedHelmetModel<>(modelSet.getModelPart(HornedHelmetModel.HORNED_HELMET_LAYER));
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.HEAD);
        if (itemStack.isIn(GoatTags.Items.HORNED_HELMETS)) {
            matrixStack.push();
            this.getContextModel().copyStateTo(this.model);
            this.getContextModel().getHead().rotate(matrixStack);
            if (livingEntity.isBaby()) matrixStack.translate(0.0D, -0.15D, 0.0D);
            this.model.setAngles(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            var vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(HORNED_HELMET_TEXTURE), false, itemStack.hasGlint());
            this.model.render(matrixStack, vertexConsumer, packedLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.pop();
        }
    }
}
