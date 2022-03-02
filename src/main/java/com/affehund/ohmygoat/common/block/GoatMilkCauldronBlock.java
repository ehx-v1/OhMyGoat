package com.affehund.ohmygoat.common.block;

import com.affehund.ohmygoat.common.block.entity.GoatMilkCauldronBlockEntity;
import com.affehund.ohmygoat.core.GoatRegistry;
import com.affehund.ohmygoat.core.util.GoatUtilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Predicate;

public class GoatMilkCauldronBlock extends LayeredCauldronBlock implements EntityBlock {
    public GoatMilkCauldronBlock(Properties properties, Predicate<Biome.Precipitation> precipitationPredicate, Map<Item, CauldronInteraction> interactionMap) {
        super(properties, precipitationPredicate, interactionMap);
    }

    @Override
    public @NotNull Item asItem() {
        return Items.CAULDRON;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new GoatMilkCauldronBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, GoatRegistry.GOAT_MILK_CAULDRON_BLOCK_ENTITY.get(), GoatMilkCauldronBlockEntity::cookTick);
    }

    @Nullable
    private static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> entityTypeA, BlockEntityType<E> entityTypeB, BlockEntityTicker<? super E> ticker) {
        return entityTypeB == entityTypeA ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
        super.entityInside(state, level, pos, entity);

        if (!level.isClientSide && this.isEntityInsideContent(state, pos, entity)) {
            if (entity instanceof LivingEntity livingEntity && GoatUtilities.cureBadEffects(livingEntity)) {
                if (entity.mayInteract(level, pos)) lowerFillLevel(state, level, pos);
            }
        }
    }
}
