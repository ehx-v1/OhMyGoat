package com.affehund.ohmygoat.common.block;

import com.affehund.ohmygoat.common.block.entity.GoatMilkCauldronBlockEntity;
import com.affehund.ohmygoat.core.GoatRegistry;
import com.affehund.ohmygoat.core.GoatUtilities;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Predicate;

public class GoatMilkCauldronBlock extends LeveledCauldronBlock implements BlockEntityProvider {

    public GoatMilkCauldronBlock(Settings settings, Predicate<Biome.Precipitation> precipitationPredicate, Map<Item, CauldronBehavior> behaviorMap) {
        super(settings, precipitationPredicate, behaviorMap);
    }

    @Override
    public Item asItem() {
        return Items.CAULDRON;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GoatMilkCauldronBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkType(type, GoatMilkCauldronBlockEntity::cookTick);
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(BlockEntityType<A> givenType, BlockEntityTicker<? super E> ticker) {
        return GoatRegistry.GOAT_MILK_CAULDRON_BLOCK_ENTITY == givenType ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);

        if (!world.isClient && this.isEntityTouchingFluid(state, pos, entity)) {
            if (entity instanceof LivingEntity livingEntity && GoatUtilities.cureBadEffects(livingEntity)) {
                if (entity.canModifyAt(world, pos)) decrementFluidLevel(state, world, pos);
            }
        }
    }
}
