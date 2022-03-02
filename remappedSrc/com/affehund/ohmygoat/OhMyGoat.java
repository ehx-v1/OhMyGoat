package com.affehund.ohmygoat;

import com.affehund.ohmygoat.core.GoatRegistry;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OhMyGoat implements ModInitializer {
    public static final String MOD_ID = "ohmygoat";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.debug("Loading up {}!", MOD_ID);
        GoatRegistry.registerBlocks();
        GoatRegistry.registerBlockEntities();
        GoatRegistry.registerCauldronBehaviours();
        GoatRegistry.registerItems();
        GoatRegistry.registerPotions();
        GoatRegistry.registerCriteria();

        LOGGER.debug("{} has finished loading for now!", MOD_ID);
    }
}
