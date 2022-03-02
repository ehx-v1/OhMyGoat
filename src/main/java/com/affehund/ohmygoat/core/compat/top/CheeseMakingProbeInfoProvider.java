package com.affehund.ohmygoat.core.compat.top;

import com.affehund.ohmygoat.OhMyGoat;
import com.affehund.ohmygoat.common.block.entity.GoatMilkCauldronBlockEntity;
import com.affehund.ohmygoat.core.GoatRegistry;
import mcjty.theoneprobe.api.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Function;

public class CheeseMakingProbeInfoProvider implements IProbeInfoProvider, Function<ITheOneProbe, Void> {
    @Override
    public Void apply(ITheOneProbe top) {
        top.registerProvider(this);
        return null;
    }

    @Override
    public ResourceLocation getID() {
        return new ResourceLocation(OhMyGoat.MOD_ID, "goat_milk_cauldron");
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof GoatMilkCauldronBlockEntity blockEntity) {
            int cauldronLevel = blockEntity.getBlockState().getValue(LayeredCauldronBlock.LEVEL);
            if (cauldronLevel > 0) {
                probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
                        .item(new ItemStack(GoatRegistry.GOAT_MILK_BUCKET.get()))
                        .text(" -> ")
                        .item(new ItemStack(GoatRegistry.GOAT_CHEESE.get()));

                float cookingProgress = blockEntity.getCookingProgress();
                if (cookingProgress > 1E-6F) {
                    probeInfo.progress((int) (cookingProgress * 100), 100, probeInfo.defaultProgressStyle().suffix("%"));
                }

                if (player.isShiftKeyDown()) {
                    float progress = (float) cauldronLevel / 3;
                    if (progress > 1E-6F) {
                        probeInfo.progress((int) (progress * 1000), 100, probeInfo.defaultProgressStyle().suffix("mb"));
                    }
                }
            }
        }
    }
}
