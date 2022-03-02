package com.affehund.ohmygoat;

import com.affehund.ohmygoat.client.layer.HornedHelmetLayer;
import com.affehund.ohmygoat.client.model.HornedHelmetModel;
import com.affehund.ohmygoat.core.GoatRegistry;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = OhMyGoat.MOD_ID)
public class ClientEventHandler {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HornedHelmetModel.HORNED_HELMET_LAYER, HornedHelmetModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerItemColors(ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, layer) -> layer != 0 ? -1 : ((DyeableArmorItem) stack.getItem())
                .getColor(stack), GoatRegistry.HORNED_LEATHER_HELMET.get());
    }

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        var entityModels = event.getEntityModels();

        var entityTypes = ImmutableList.copyOf(ForgeRegistries.ENTITIES.getValues().stream()
                .filter(DefaultAttributes::hasSupplier).map(entityType -> (EntityType<? extends LivingEntity>) entityType)
                .filter(entityType -> entityType != EntityType.ENDER_DRAGON).toList());

        for (var entityType : entityTypes) {
            LivingEntityRenderer renderer = null;
            try {
                renderer = event.getRenderer(entityType);
            } catch (Exception e) {
                OhMyGoat.LOGGER.debug("Could not add goat horn layer to {} as it uses a custom renderer.", entityType.getRegistryName());
            }
            if (renderer instanceof HumanoidMobRenderer || renderer instanceof ArmorStandRenderer) {
                renderer.addLayer(new HornedHelmetLayer(renderer, entityModels));
            }
        }

        for (String skin : event.getSkins()) {
            var playerRenderer = event.getSkin(skin);
            playerRenderer.addLayer(new HornedHelmetLayer(playerRenderer, entityModels));
        }
    }
}
