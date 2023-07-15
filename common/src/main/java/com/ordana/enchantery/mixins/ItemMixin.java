package com.ordana.enchantery.mixins;

import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Shadow public abstract boolean canBeDepleted();

    @Shadow public abstract int getMaxStackSize();

    @Inject(method = "getEnchantmentValue", at = @At(value = "TAIL"), cancellable = true)
    public void anythingEnchantable(CallbackInfoReturnable<Integer> cir) {
        if (this.getMaxStackSize() == 1 && this.canBeDepleted()) cir.setReturnValue(5);
    }

}
