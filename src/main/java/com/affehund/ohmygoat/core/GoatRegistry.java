package com.affehund.ohmygoat.core;

import com.affehund.ohmygoat.OhMyGoat;
import com.affehund.ohmygoat.common.block.GoatMilkCauldronBlock;
import com.affehund.ohmygoat.common.block.entity.GoatMilkCauldronBlockEntity;
import com.affehund.ohmygoat.common.item.GoatHornItem;
import com.affehund.ohmygoat.common.item.GoatMilkBucketItem;
import com.affehund.ohmygoat.core.util.SmithItemTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static net.minecraft.world.level.block.Blocks.CAULDRON;

public class GoatRegistry {
    public static final CreativeModeTab OH_MY_GOAT_TAB = new CreativeModeTab(OhMyGoat.MOD_ID + "." + OhMyGoat.MOD_ID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return ItemStack.EMPTY;
        }
    };


    /*
    Blocks and Block Entities
     */

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, OhMyGoat.MOD_ID);
    public static final RegistryObject<Block> GOAT_MILK_CAULDRON_BLOCK = BLOCKS.register("goat_milk_cauldron", () -> new GoatMilkCauldronBlock(BlockBehaviour.Properties.copy(CAULDRON), (precipitation) -> false, GoatRegistry.GOAT_MILK_CAULDRON_INTERACTION));

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, OhMyGoat.MOD_ID);
    public static final RegistryObject<BlockEntityType<GoatMilkCauldronBlockEntity>> GOAT_MILK_CAULDRON_BLOCK_ENTITY = BLOCK_ENTITIES.register("goat_milk_cauldron", () -> BlockEntityType.Builder.of(GoatMilkCauldronBlockEntity::new, GOAT_MILK_CAULDRON_BLOCK.get()).build(null));


    /*
    Items
     */

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OhMyGoat.MOD_ID);

    public static final RegistryObject<Item> CHEVON = ITEMS.register("chevon", () -> new Item(new Item.Properties().tab(OH_MY_GOAT_TAB).food(new FoodProperties.Builder().nutrition(3).saturationMod(0.2f).meat().build())));
    public static final RegistryObject<Item> COOKED_CHEVON = ITEMS.register("cooked_chevon", () -> new Item(new Item.Properties().tab(OH_MY_GOAT_TAB).food(new FoodProperties.Builder().nutrition(7).saturationMod(0.6f).meat().build())));
    public static final RegistryObject<Item> GOAT_CHEESE = ITEMS.register("goat_cheese", () -> new Item(new Item.Properties().tab(OH_MY_GOAT_TAB).food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).alwaysEat().build())));
    public static final RegistryObject<Item> GOAT_HORN = ITEMS.register("goat_horn", () -> new GoatHornItem(new Item.Properties().tab(OH_MY_GOAT_TAB).stacksTo(1).durability(59).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> GOAT_MILK_BUCKET = ITEMS.register("goat_milk_bucket", () -> new GoatMilkBucketItem(new Item.Properties().tab(OH_MY_GOAT_TAB).stacksTo(1).craftRemainder(Items.BUCKET)));

    public static final RegistryObject<Item> HORNED_CHAINMAIL_HELMET = ITEMS.register("horned_chainmail_helmet", () -> new ArmorItem(ArmorMaterials.CHAIN, EquipmentSlot.HEAD, new Item.Properties().tab(OH_MY_GOAT_TAB)));
    public static final RegistryObject<Item> HORNED_DIAMOND_HELMET = ITEMS.register("horned_diamond_helmet", () -> new ArmorItem(ArmorMaterials.DIAMOND, EquipmentSlot.HEAD, new Item.Properties().tab(OH_MY_GOAT_TAB)));
    public static final RegistryObject<Item> HORNED_GOLDEN_HELMET = ITEMS.register("horned_golden_helmet", () -> new ArmorItem(ArmorMaterials.GOLD, EquipmentSlot.HEAD, new Item.Properties().tab(OH_MY_GOAT_TAB)));
    public static final RegistryObject<Item> HORNED_IRON_HELMET = ITEMS.register("horned_iron_helmet", () -> new ArmorItem(ArmorMaterials.IRON, EquipmentSlot.HEAD, new Item.Properties().tab(OH_MY_GOAT_TAB)));
    public static final RegistryObject<Item> HORNED_LEATHER_HELMET = ITEMS.register("horned_leather_helmet", () -> new DyeableArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.HEAD, new Item.Properties().tab(OH_MY_GOAT_TAB)));
    public static final RegistryObject<Item> HORNED_NETHERITE_HELMET = ITEMS.register("horned_netherite_helmet", () -> new ArmorItem(ArmorMaterials.NETHERITE, EquipmentSlot.HEAD, new Item.Properties().tab(OH_MY_GOAT_TAB).fireResistant()));

    /*
    Brewing Recipes
     */

    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, OhMyGoat.MOD_ID);

    public static final RegistryObject<Potion> GOAT_SCREAM_POTION = POTIONS.register("goat_scream", () -> new Potion("goat_scream", new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 300, 1), new MobEffectInstance(MobEffects.BLINDNESS, 300)));
    public static final RegistryObject<Potion> LONG_GOAT_SCREAM_POTION = POTIONS.register("long_goat_scream", () -> new Potion("goat_scream", new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 1), new MobEffectInstance(MobEffects.BLINDNESS, 600)));
    public static final RegistryObject<Potion> STRONG_GOAT_SCREAM_POTION = POTIONS.register("strong_goat_scream", () -> new Potion("goat_scream", new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 300, 3), new MobEffectInstance(MobEffects.BLINDNESS, 300, 1)));

    public static void registerBrewingRecipes() {
        PotionBrewing.addMix(Potions.AWKWARD, GOAT_HORN.get(), GOAT_SCREAM_POTION.get());
        PotionBrewing.addMix(GOAT_SCREAM_POTION.get(), Items.REDSTONE, LONG_GOAT_SCREAM_POTION.get());
        PotionBrewing.addMix(GOAT_SCREAM_POTION.get(), Items.GLOWSTONE_DUST, STRONG_GOAT_SCREAM_POTION.get());
    }

    /*
    Criteria Triggers
     */

    public static SmithItemTrigger SMITH_ITEM_TRIGGER = new SmithItemTrigger();

    public static void registerCriteriaTriggers() {
        CriteriaTriggers.register(SMITH_ITEM_TRIGGER);
    }


    /*
    Cauldron Interactions
     */

    public static final Map<Item, CauldronInteraction> GOAT_MILK_CAULDRON_INTERACTION = CauldronInteraction.newInteractionMap();
    public static final CauldronInteraction EMPTY_GOAT_MILK_CAULDRON_INTERACTION = (state, world, pos, player, hand, stack) -> CauldronInteraction.fillBucket(state, world, pos, player, hand, stack, new ItemStack(GoatRegistry.GOAT_MILK_BUCKET.get()), blockState -> blockState.getValue(LayeredCauldronBlock.LEVEL) == 3, SoundEvents.BUCKET_FILL);
    public static final CauldronInteraction FILL_GOAT_MILK_CAULDRON_INTERACTION = (state, level, pos, player, hand, stack) -> CauldronInteraction.emptyBucket(level, pos, player, hand, stack, GoatRegistry.GOAT_MILK_CAULDRON_BLOCK.get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3), SoundEvents.BUCKET_EMPTY);

    public static void registerCauldronInteractions() {
        CauldronInteraction.addDefaultInteractions(GoatRegistry.GOAT_MILK_CAULDRON_INTERACTION);

        CauldronInteraction.EMPTY.put(GoatRegistry.GOAT_MILK_BUCKET.get(), GoatRegistry.FILL_GOAT_MILK_CAULDRON_INTERACTION);
        CauldronInteraction.WATER.put(GoatRegistry.GOAT_MILK_BUCKET.get(), GoatRegistry.FILL_GOAT_MILK_CAULDRON_INTERACTION);
        CauldronInteraction.LAVA.put(GoatRegistry.GOAT_MILK_BUCKET.get(), GoatRegistry.FILL_GOAT_MILK_CAULDRON_INTERACTION);
        CauldronInteraction.POWDER_SNOW.put(GoatRegistry.GOAT_MILK_BUCKET.get(), GoatRegistry.FILL_GOAT_MILK_CAULDRON_INTERACTION);

        GoatRegistry.GOAT_MILK_CAULDRON_INTERACTION.put(GoatRegistry.GOAT_MILK_BUCKET.get(), GoatRegistry.FILL_GOAT_MILK_CAULDRON_INTERACTION);
        GoatRegistry.GOAT_MILK_CAULDRON_INTERACTION.put(Items.BUCKET, GoatRegistry.EMPTY_GOAT_MILK_CAULDRON_INTERACTION);
    }
}
