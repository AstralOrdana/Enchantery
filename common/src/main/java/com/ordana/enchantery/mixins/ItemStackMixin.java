package com.ordana.enchantery.mixins;

import com.ordana.enchantery.reg.ModEnchants;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @ModifyVariable(
            method = "hurt",
            at = @At(value = "HEAD"), index = 1, argsOnly = true)
    public int damage(int amount) {
        int f = EnchantmentHelper.getItemEnchantmentLevel(ModEnchants.DIMINISHING_CURSE.get(), (ItemStack)(Object) this);
        if (f > 0) amount = amount * 2;
        return amount;
    }
}
