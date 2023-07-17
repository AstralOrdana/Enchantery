package com.ordana.enchantery.mixins;

import com.ordana.enchantery.EnchanteryLogic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BowItem.class)
public class BowItemMixin {

    @Unique
    protected ItemStack bowStack;

    @Inject(method = "releaseUsing", at = @At("HEAD"), locals = LocalCapture.CAPTURE_FAILSOFT)
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged, CallbackInfo ci) {
        bowStack = stack;
    }

    @Inject(method = "releaseUsing", at = @At("TAIL"))
    public void doKickback(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged, CallbackInfo ci) {
        EnchanteryLogic.kickbackCurseLogic(livingEntity, stack);
    }

    @ModifyArg(method = "releaseUsing", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;shootFromRotation(Lnet/minecraft/world/entity/Entity;FFFFF)V"
    ), index = 5)
    public float arrowAccuracy(float accuracy) {
        return EnchanteryLogic.imprecisionCurseLogic(bowStack);
    }
}
