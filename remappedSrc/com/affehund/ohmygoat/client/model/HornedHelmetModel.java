package com.affehund.ohmygoat.client.model;

import com.affehund.ohmygoat.OhMyGoat;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class HornedHelmetModel<T extends LivingEntity> extends AnimalModel<T> {
    public static final EntityModelLayer HORNED_HELMET_LAYER = new EntityModelLayer(new Identifier(OhMyGoat.MOD_ID, "horned_helmet"), "main");
    private final ModelPart horns;

    public HornedHelmetModel(ModelPart root) {
        this.horns = root.getChild("horns");
    }

    public static TexturedModelData getTexturedModelData() {
        var meshDefinition = new ModelData();
        var partDefinition = meshDefinition.getRoot();
        var cubeDefinition = new Dilation(1.0F);

        var horns = partDefinition.addChild("horns", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        horns.addChild("right_horn", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -5.0F, -1.0F, 2.0F, 5.0F, 2.0F, cubeDefinition), ModelTransform.of(-3.0F, -33.75F, -2.0F, 0.0F, -0.0873F, -0.2182F));
        horns.addChild("left_horn", ModelPartBuilder.create().uv(6, 5).cuboid(-1.0F, -5.0F, -1.0F, 2.0F, 5.0F, 2.0F, cubeDefinition), ModelTransform.of(3.0F, -33.75F, -2.0F, 0.0F, 0.0873F, 0.2182F));

        return TexturedModelData.of(meshDefinition, 16, 16);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(horns);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
