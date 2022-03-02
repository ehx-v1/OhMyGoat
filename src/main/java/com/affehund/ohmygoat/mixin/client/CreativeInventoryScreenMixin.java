package com.affehund.ohmygoat.mixin.client;

import com.affehund.ohmygoat.core.GoatRegistry;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> {

    @Shadow
    public abstract int getSelectedTab();

    public CreativeInventoryScreenMixin(CreativeInventoryScreen.CreativeScreenHandler screenHandler, PlayerInventory inventory, LiteralText text) {
        super(screenHandler, inventory, text);
    }

    @Inject(method = "renderTabIcon(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/item/ItemGroup;)V", at = @At("HEAD"), cancellable = true)
    private void renderTabIcon(MatrixStack matrices, ItemGroup group, CallbackInfo ci) {
        if (group.equals(GoatRegistry.OH_MY_GOAT_GROUP) && group.getIcon().equals(ItemStack.EMPTY)) {
            ci.cancel();
            boolean flag = group.getIndex() == this.getSelectedTab();
            boolean flag1 = group.isTopRow();
            int i = group.getColumn();
            int j = i * 28;
            int k = 0;
            int l = this.x + 28 * i;
            int m = this.y;
            if (flag) {
                k += 32;
            }

            if (group.isSpecial()) {
                l = this.x + this.backgroundWidth - 28 * (6 - i);
            } else if (i > 0) {
                l += i;
            }

            if (flag1) {
                m -= 28;
            } else {
                k += 64;
                m += this.backgroundHeight - 4;
            }

            this.drawTexture(matrices, l, m, j, k, 28, 32);

            m += 22 + (flag1 ? 1 : -1);
            l += 14;

            assert client != null;
            assert client.world != null;
            InventoryScreen.drawEntity(l, m, 10, (float) (client.getWindow().getScaledWidth() - client.mouse.getX()), (float) (client.getWindow().getScaledHeight() - client.mouse.getY()), Objects.requireNonNull(EntityType.GOAT.create(client.world)));
        }
    }
}
