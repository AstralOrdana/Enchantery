package com.ordana.enchantery.mixins;

import com.ordana.enchantery.reg.ModEnchants;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;

@Mixin(Inventory.class)
public class InventoryMixin {

    /*
    @Inject(method = "dropAll", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z",
            shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void dontDropCompass(CallbackInfo ci, Iterator var1, List list, int i, ItemStack itemStack) {
        int f = EnchantmentHelper.getItemEnchantmentLevel(ModEnchants.SOULBOUND.get(), itemStack);
        if (f > 0) return;
    }
     */

    @Redirect(method = "dropAll",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z"))
    private boolean dontDropCompass(ItemStack itemStack) {
        int f = EnchantmentHelper.getItemEnchantmentLevel(ModEnchants.SOULBOUND.get(), itemStack);
        return (f > 0);
    }

}
