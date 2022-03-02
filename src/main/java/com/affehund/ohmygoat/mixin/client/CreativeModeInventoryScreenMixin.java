package com.affehund.ohmygoat.mixin.client;

import com.affehund.ohmygoat.core.GoatRegistry;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin extends EffectRenderingInventoryScreen<CreativeModeInventoryScreen.ItemPickerMenu> {
    @Shadow
    public abstract int getSelectedTab();

    public CreativeModeInventoryScreenMixin(CreativeModeInventoryScreen.ItemPickerMenu picker, Inventory inventory, Component component) {
        super(picker, inventory, component);
    }

    @Inject(method = "renderTabButton(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/item/CreativeModeTab;)V", at = @At("HEAD"), cancellable = true)
    private void renderTabButton(PoseStack poseStack, CreativeModeTab tab, CallbackInfo ci) {
        if (tab.equals(GoatRegistry.OH_MY_GOAT_TAB)) {
            ci.cancel();
            boolean flag = tab.getId() == this.getSelectedTab();
            boolean flag1 = tab.isTopRow();
            int i = tab.getColumn();
            int j = i * 28;
            int k = 0;
            int l = this.leftPos + 28 * i;
            int i1 = this.topPos;
            if (flag) {
                k += 32;
            }

            if (tab.isAlignedRight()) {
                l = this.leftPos + this.imageWidth - 28 * (6 - i);
            } else if (i > 0) {
                l += i;
            }

            if (flag1) {
                i1 -= 28;
            } else {
                k += 64;
                i1 += this.imageHeight - 4;
            }

            RenderSystem.enableBlend(); //Forge: Make sure blend is enabled else tabs show a white border.
            this.blit(poseStack, l, i1, j, k, 28, 32);

            i1 += 22 + (flag1 ? 1 : -1);
            l += 14;

            assert minecraft != null;
            assert minecraft.level != null;

            InventoryScreen.renderEntityInInventory(l, i1, 10, (float) (minecraft.getWindow().getGuiScaledWidth() - minecraft.mouseHandler.xpos()), (float) (minecraft.getWindow().getGuiScaledHeight() - minecraft.mouseHandler.ypos()), Objects.requireNonNull(EntityType.GOAT.create(minecraft.level)));
        }
    }
}
