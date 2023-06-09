package com.ordana.enchantery.mixins;

import com.ordana.enchantery.EnchanteryLogic;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(EnchantmentMenu.class)
public abstract class EnchantmentMenuMixin extends AbstractContainerMenu {

    @Shadow
    @Final
    private RandomSource random;

    @Final
    @Shadow
    private ContainerLevelAccess access;

    @Shadow @Final public int[] levelClue;

    protected EnchantmentMenuMixin(@Nullable MenuType<?> menuType, int i) {
        super(menuType, i);
    }

    @Inject(method = "getEnchantmentList",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z",
                    shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void enchListMixin(ItemStack stack, int enchantSlot, int level, CallbackInfoReturnable<List<EnchantmentInstance>> cir,
                               List<EnchantmentInstance> list) {
        EnchanteryLogic.modifyEnchantmentList(this.access, this.random, stack, list);
    }


    @Inject(method = "clickMenuButton",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getCount()I",
                    shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void internalXp(Player player, int id, CallbackInfoReturnable<Boolean> cir, ItemStack itemStack, ItemStack itemStack2, int i) {
        access.evaluate((level, pos) ->
                EnchanteryLogic.getCharge(level, pos)
        );

    }

}