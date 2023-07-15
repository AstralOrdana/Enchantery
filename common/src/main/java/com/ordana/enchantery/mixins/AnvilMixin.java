package com.ordana.enchantery.mixins;

import com.ordana.enchantery.configs.CommonConfigs;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(AnvilMenu.class)
public class AnvilMixin {

    @Shadow @Final private DataSlot cost;
    @Shadow private int repairItemCountCost;

    @Redirect(method = "createResult", at = @At(
            value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    private boolean particleTint(ItemStack instance, Item item) {
        return !CommonConfigs.DISABLE_ANVIL_ENCHANTING.get();
    }

    @Inject(method = "createResult",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/inventory/AnvilMenu;broadcastChanges()V",
                    shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILSOFT)
    public void priceless(CallbackInfo ci) {
        if (CommonConfigs.DISABLE_ANVIL_COST.get()) cost.set(0);
    }


    @Inject(method = "mayPickup", at = @At(value = "HEAD"), cancellable = true)
    protected void mayPickup(Player player, boolean hasStack, CallbackInfoReturnable<Boolean> cir) {
        if (CommonConfigs.DISABLE_ANVIL_COST.get()) cir.setReturnValue(true);
    }

    @Inject(method = "calculateIncreasedRepairCost", at = @At(value = "HEAD"), cancellable = true)
    private static void increaseLimit(int oldRepairCost, CallbackInfoReturnable<Integer> cir) {
        if (CommonConfigs.DISABLE_ANVIL_COST.get()) cir.setReturnValue(0);
    }

}
