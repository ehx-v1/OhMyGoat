package com.affehund.ohmygoat.core;

import com.affehund.ohmygoat.OhMyGoat;
import com.affehund.ohmygoat.common.block.GoatMilkCauldronBlock;
import com.affehund.ohmygoat.common.block.entity.GoatMilkCauldronBlockEntity;
import com.affehund.ohmygoat.common.item.GoatHornItem;
import com.affehund.ohmygoat.common.item.GoatMilkBucketItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class GoatRegistry {
    public static final ItemGroup OH_MY_GOAT_GROUP = FabricItemGroupBuilder.build(
            new Identifier(OhMyGoat.MOD_ID, OhMyGoat.MOD_ID),
            () -> ItemStack.EMPTY);

    public static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();
    public static final Block GOAT_MILK_CAULDRON_BLOCK = add("goat_milk_cauldron", new GoatMilkCauldronBlock(AbstractBlock.Settings.copy(Blocks.CAULDRON), (precipitation) -> false, GoatRegistry.GOAT_MILK_BEHAVIOR));

    public static final Map<Identifier, BlockEntityType<?>> BLOCK_ENTITIES = new LinkedHashMap<>();
    public static final BlockEntityType<GoatMilkCauldronBlockEntity> GOAT_MILK_CAULDRON_BLOCK_ENTITY = add("goat_milk_cauldron", FabricBlockEntityTypeBuilder.create(GoatMilkCauldronBlockEntity::new, GOAT_MILK_CAULDRON_BLOCK).build(null));

    public static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();
    public static final Item CHEVON = add("chevon", new Item(new FabricItemSettings().group(OH_MY_GOAT_GROUP).food(new FoodComponent.Builder().hunger(3).saturationModifier(0.2f).meat().build())));
    public static final Item COOKED_CHEVON = add("cooked_chevon", new Item(new FabricItemSettings().group(OH_MY_GOAT_GROUP).food(new FoodComponent.Builder().hunger(8).saturationModifier(0.8f).meat().build())));
    public static final Item GOAT_CHEESE = add("goat_cheese", new Item(new FabricItemSettings().group(OH_MY_GOAT_GROUP).food(new FoodComponent.Builder().hunger(4).saturationModifier(0.3f).meat().build())));
    public static final Item GOAT_HORN = add("goat_horn", new GoatHornItem(new FabricItemSettings().group(OH_MY_GOAT_GROUP).maxCount(1).maxDamage(59)));
    public static final Item GOAT_MILK_BUCKET = add("goat_milk_bucket", new GoatMilkBucketItem(new FabricItemSettings().group(OH_MY_GOAT_GROUP).maxCount(1).recipeRemainder(Items.BUCKET)));

    public static final Item HORNED_CHAINMAIL_HELMET = add("horned_chainmail_helmet", new ArmorItem(ArmorMaterials.CHAIN, EquipmentSlot.HEAD, new FabricItemSettings().group(OH_MY_GOAT_GROUP)));
    public static final Item HORNED_DIAMOND_HELMET = add("horned_diamond_helmet", new ArmorItem(ArmorMaterials.DIAMOND, EquipmentSlot.HEAD, new FabricItemSettings().group(OH_MY_GOAT_GROUP)));
    public static final Item HORNED_GOLDEN_HELMET = add("horned_golden_helmet", new ArmorItem(ArmorMaterials.GOLD, EquipmentSlot.HEAD, new FabricItemSettings().group(OH_MY_GOAT_GROUP)));
    public static final Item HORNED_IRON_HELMET = add("horned_iron_helmet", new ArmorItem(ArmorMaterials.IRON, EquipmentSlot.HEAD, new FabricItemSettings().group(OH_MY_GOAT_GROUP)));
    public static final Item HORNED_LEATHER_HELMET = add("horned_leather_helmet", new DyeableArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.HEAD, new FabricItemSettings().group(OH_MY_GOAT_GROUP)));
    public static final Item HORNED_NETHERITE_HELMET = add("horned_netherite_helmet", new ArmorItem(ArmorMaterials.NETHERITE, EquipmentSlot.HEAD, new FabricItemSettings().group(OH_MY_GOAT_GROUP)));

    public static final Map<Identifier, Potion> POTIONS = new LinkedHashMap<>();
    public static final Potion GOAT_SCREAM_POTION = add("goat_scream", new Potion("goat_scream", new StatusEffectInstance(StatusEffects.SLOWNESS, 300, 1), new StatusEffectInstance(StatusEffects.BLINDNESS, 300)));
    public static final Potion LONG_GOAT_SCREAM_POTION = add("long_goat_scream", new Potion("goat_scream", new StatusEffectInstance(StatusEffects.SLOWNESS, 600, 1), new StatusEffectInstance(StatusEffects.BLINDNESS, 600)));
    public static final Potion STRONG_GOAT_SCREAM_POTION = add("strong_goat_scream", new Potion("goat_scream", new StatusEffectInstance(StatusEffects.SLOWNESS, 300, 3), new StatusEffectInstance(StatusEffects.BLINDNESS, 300, 1)));

    public static final Map<Item, CauldronBehavior> GOAT_MILK_BEHAVIOR = CauldronBehavior.createMap();
    public static final CauldronBehavior EMPTY_GOAT_MILK_BEHAVIOR = (state, world, pos, player, hand, stack) -> CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(GoatRegistry.GOAT_MILK_BUCKET), blockState -> blockState.get(LeveledCauldronBlock.LEVEL) == 3, SoundEvents.ITEM_BUCKET_FILL);
    public static final CauldronBehavior FILL_GOAT_MILK_CAULDRON_BEHAVIOR = (state, level, pos, player, hand, stack) -> CauldronBehavior.fillCauldron(level, pos, player, hand, stack, GoatRegistry.GOAT_MILK_CAULDRON_BLOCK.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3), SoundEvents.ITEM_BUCKET_EMPTY);

    private static Item add(String name, Item item) {
        ITEMS.put(new Identifier(OhMyGoat.MOD_ID, name), item);
        return item;
    }

    private static Block add(String name, Block block) {
        BLOCKS.put(new Identifier(OhMyGoat.MOD_ID, name), block);
        return block;
    }

    private static BlockEntityType add(String name, BlockEntityType blockEntityType) {
        BLOCK_ENTITIES.put(new Identifier(OhMyGoat.MOD_ID, name), blockEntityType);
        return blockEntityType;
    }

    private static Potion add(String name, Potion potion) {
        POTIONS.put(new Identifier(OhMyGoat.MOD_ID, name), potion);
        return potion;
    }

    public static void registerBlocks() {
        OhMyGoat.LOGGER.debug("Registering blocks");
        BLOCKS.forEach((id, block) -> Registry.register(Registry.BLOCK, id, block));
    }

    public static void registerBlockEntities() {
        OhMyGoat.LOGGER.debug("Registering block entities");
        BLOCK_ENTITIES.forEach((id, blockEntityType) -> Registry.register(Registry.BLOCK_ENTITY_TYPE, id, blockEntityType));
    }

    public static void registerCauldronBehaviours() {
        CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(GoatRegistry.GOAT_MILK_BUCKET, GoatRegistry.FILL_GOAT_MILK_CAULDRON_BEHAVIOR);
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(GoatRegistry.GOAT_MILK_BUCKET, GoatRegistry.FILL_GOAT_MILK_CAULDRON_BEHAVIOR);
        CauldronBehavior.LAVA_CAULDRON_BEHAVIOR.put(GoatRegistry.GOAT_MILK_BUCKET, GoatRegistry.FILL_GOAT_MILK_CAULDRON_BEHAVIOR);
        CauldronBehavior.POWDER_SNOW_CAULDRON_BEHAVIOR.put(GoatRegistry.GOAT_MILK_BUCKET, GoatRegistry.FILL_GOAT_MILK_CAULDRON_BEHAVIOR);

        GoatRegistry.GOAT_MILK_BEHAVIOR.put(GoatRegistry.GOAT_MILK_BUCKET, GoatRegistry.FILL_GOAT_MILK_CAULDRON_BEHAVIOR);
        GoatRegistry.GOAT_MILK_BEHAVIOR.put(Items.BUCKET, GoatRegistry.EMPTY_GOAT_MILK_BEHAVIOR);

        CauldronBehavior.registerBucketBehavior(GoatRegistry.GOAT_MILK_BEHAVIOR);
    }

    public static void registerItems() {
        OhMyGoat.LOGGER.debug("Registering items");
        ITEMS.forEach((id, item) -> Registry.register(Registry.ITEM, id, item));
    }

    public static void registerPotions() {
        OhMyGoat.LOGGER.debug("Registering potions");
        POTIONS.forEach((id, potion) -> Registry.register(Registry.POTION, id, potion));

        BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, GOAT_HORN, GOAT_SCREAM_POTION);
        BrewingRecipeRegistry.registerPotionRecipe(GOAT_SCREAM_POTION, Items.REDSTONE, LONG_GOAT_SCREAM_POTION);
        BrewingRecipeRegistry.registerPotionRecipe(GOAT_SCREAM_POTION, Items.GLOWSTONE_DUST, STRONG_GOAT_SCREAM_POTION);
    }

    public static SmithItemCriterion SMITH_ITEM_TRIGGER = new SmithItemCriterion();

    public static void registerCriteria() {
        Criteria.register(SMITH_ITEM_TRIGGER);
    }
}
