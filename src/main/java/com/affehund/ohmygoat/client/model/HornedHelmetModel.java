package com.affehund.ohmygoat.client.model;

import com.affehund.ohmygoat.OhMyGoat;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class HornedHelmetModel<T extends LivingEntity> extends AgeableListModel<T> {
    public static final ModelLayerLocation HORNED_HELMET_LAYER = new ModelLayerLocation(new ResourceLocation(OhMyGoat.MOD_ID, "horned_helmet"), "main");
    private final ModelPart horns;

    public HornedHelmetModel(ModelPart root) {
        this.horns = root.getChild("horns");
    }

    public static LayerDefinition createBodyLayer() {
        var meshDefinition = new MeshDefinition();
        var partDefinition = meshDefinition.getRoot();
        var cubeDefinition = new CubeDeformation(1.0F);

        var horns = partDefinition.addOrReplaceChild("horns", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        horns.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 5.0F, 2.0F, cubeDefinition), PartPose.offsetAndRotation(-3.0F, -33.75F, -2.0F, 0.0F, -0.0873F, -0.2182F));
        horns.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(6, 5).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 5.0F, 2.0F, cubeDefinition), PartPose.offsetAndRotation(3.0F, -33.75F, -2.0F, 0.0F, 0.0873F, 0.2182F));

        return LayerDefinition.create(meshDefinition, 16, 16);
    }

    @Override
    protected @NotNull Iterable<ModelPart> headParts() {
        return ImmutableList.of();
    }

    @Override
    protected @NotNull Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(horns);
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }
}
