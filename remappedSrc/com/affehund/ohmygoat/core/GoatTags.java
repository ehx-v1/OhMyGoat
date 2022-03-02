package com.affehund.ohmygoat.core;

import com.affehund.ohmygoat.OhMyGoat;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class GoatTags {
    public static class Items {
        public static final Tag<Item> HORNABLE_HELMETS = modTag("hornable_helmets");
        public static final Tag<Item> HORNED_HELMETS = modTag("horned_helmets");

        private static Tag<Item> modTag(String name) {
            return tag(new Identifier(OhMyGoat.MOD_ID, name));
        }

        private static Tag<Item> tag(Identifier id) {
            return TagFactory.ITEM.create(id);
        }
    }
}
