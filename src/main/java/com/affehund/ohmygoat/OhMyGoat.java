package com.affehund.ohmygoat;

import com.affehund.ohmygoat.core.GoatDataGenerator;
import com.affehund.ohmygoat.core.GoatRegistry;
import com.affehund.ohmygoat.core.compat.top.CheeseMakingProbeInfoProvider;
import com.affehund.ohmygoat.core.util.GoatUtilities;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(OhMyGoat.MOD_ID)
public class OhMyGoat {
    public static final String MOD_ID = "ohmygoat";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public OhMyGoat() {
        LOGGER.debug("Loading up {}!", MOD_ID);
        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        var forgeEventBus = MinecraftForge.EVENT_BUS;

        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::enqueueIMCMessage);
        modEventBus.addListener(this::gatherData);

        GoatRegistry.BLOCKS.register(modEventBus);
        GoatRegistry.BLOCK_ENTITIES.register(modEventBus);
        GoatRegistry.ITEMS.register(modEventBus);
        GoatRegistry.POTIONS.register(modEventBus);

        forgeEventBus.register(this);
        LOGGER.debug("{} has finished loading for now!", MOD_ID);
    }

    private void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            GoatRegistry.registerBrewingRecipes();
            GoatRegistry.registerCauldronInteractions();
            GoatRegistry.registerCriteriaTriggers();
        });
    }

    private void enqueueIMCMessage(InterModEnqueueEvent event) {
        var TOP = "theoneprobe";
        if (GoatUtilities.isModLoaded(TOP)) {
            InterModComms.sendTo(TOP, "getTheOneProbe", CheeseMakingProbeInfoProvider::new);
        }
    }

    private void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var existingFileHelper = event.getExistingFileHelper();

        if (event.includeClient()) {
            generator.addProvider(new GoatDataGenerator.BlockStateGenerator(generator, MOD_ID, existingFileHelper));
            generator.addProvider(new GoatDataGenerator.ItemModelGenerator(generator, MOD_ID, existingFileHelper));
            generator.addProvider(new GoatDataGenerator.LanguageGenerator(generator, MOD_ID));
        }

        if (event.includeServer()) {
            generator.addProvider(new GoatDataGenerator.AdvancementGenerator(generator, existingFileHelper));
            var blockTagsProvider = new GoatDataGenerator.BlockTagsGenerator(generator, MOD_ID, existingFileHelper);
            generator.addProvider(blockTagsProvider);
            generator.addProvider(new GoatDataGenerator.ItemTagsGenerator(generator, blockTagsProvider, MOD_ID, existingFileHelper));
            generator.addProvider(new GoatDataGenerator.LootTableGenerator(generator));
            generator.addProvider(new GoatDataGenerator.RecipeGenerator(generator));
        }
    }
}
