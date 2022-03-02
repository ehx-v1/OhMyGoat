package com.affehund.ohmygoat.core.util;

import com.affehund.ohmygoat.OhMyGoat;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class GoatTags {
    public static class Blocks {
        public static final Tag.Named<Block> CAULDRONS = tag("cauldrons");
        public static final Tag.Named<Block> MINEABLE_PICKAXE = tag("mineable/pickaxe");

        private static Tag.Named<Block> tag(String name) {
            return BlockTags.bind(name);
        }
    }

    public static class Items {
        public static final Tag.Named<Item> HORNABLE_HELMETS = modTag("hornable_helmets");
        public static final Tag.Named<Item> HORNED_HELMETS = modTag("horned_helmets");
        public static final Tag.Named<Item> PIGLIN_LOVED = tag("piglin_loved");

        private static Tag.Named<Item> modTag(String name) {
            return tag(OhMyGoat.MOD_ID + ":" + name);
        }

        private static Tag.Named<Item> tag(String name) {
            return ItemTags.bind(name);
        }
    }
}
