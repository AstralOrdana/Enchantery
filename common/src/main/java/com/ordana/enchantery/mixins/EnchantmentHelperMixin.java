package com.ordana.enchantery.mixins;

import com.ordana.enchantery.EnchanteryLogic;
import com.ordana.enchantery.reg.ModTags;
import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;


@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @Redirect(
            method = "getAvailableEnchantmentResults",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/Enchantment;isDiscoverable()Z"))
    private static boolean tagCheck(Enchantment enchantment) {
        return EnchanteryLogic.getHolder(enchantment).is(ModTags.BASIC);
    }

}
