package com.ordana.enchantery.mixins;

import com.ordana.enchantery.EnchanteryLogic;
import com.ordana.enchantery.reg.ModEnchants;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SwordItem.class)
public class SwordItemMixin {

    @Shadow @Final private float attackDamage;

    @Inject(method = "hurtEnemy", at = @At("HEAD"))
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker, CallbackInfoReturnable<Boolean> cir) {
        int f = EnchantmentHelper.getItemEnchantmentLevel(ModEnchants.BACKBITING_CURSE.get(), stack);
        if (f > 0) attacker.hurt(attacker.damageSources().thorns(attacker), this.attackDamage * f / 2);
    }
}
