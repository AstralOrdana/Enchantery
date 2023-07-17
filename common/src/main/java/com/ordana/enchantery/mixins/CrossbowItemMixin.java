package com.ordana.enchantery.mixins;

import com.ordana.enchantery.EnchanteryLogic;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CrossbowItem;setCharged(Lnet/minecraft/world/item/ItemStack;Z)V", shift = At.Shift.AFTER))
    public void doKickback(Level level, Player player, InteractionHand usedHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack stack = player.getItemInHand(usedHand);
        EnchanteryLogic.kickbackCurseLogic(player, stack);
    }
}
