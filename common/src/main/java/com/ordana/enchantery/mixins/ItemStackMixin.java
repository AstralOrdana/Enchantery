package com.ordana.enchantery.mixins;

import com.ordana.enchantery.EnchanteryLogic;
import com.ordana.enchantery.reg.ModEnchants;
import com.ordana.enchantery.reg.ModTags;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "appendEnchantmentNames", at = @At(value = "TAIL"))
    private static void enchantmentTooltips(List<Component> tooltipComponents, ListTag storedEnchantments, CallbackInfo ci) {

        if (storedEnchantments.size() > 0 && !Screen.hasShiftDown()) tooltipComponents.add(Component.literal("[+]").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN)));

        for (int i = 0; i < storedEnchantments.size(); ++i) {
            if (Screen.hasShiftDown()) {
                CompoundTag compoundTag = storedEnchantments.getCompound(i);
                Registry.ENCHANTMENT.getOptional(EnchantmentHelper.getEnchantmentId(compoundTag)).ifPresent((enchantment) -> {

                    if (EnchanteryLogic.getHolder(enchantment).is(ModTags.BASIC)) {
                        tooltipComponents.add(Component.translatable(enchantment.getDescriptionId()).setStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_PURPLE)));
                        tooltipComponents.add(Component.translatable("tooltip.enchantery.basic").setStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_PURPLE)));
                    }
                    if (EnchanteryLogic.getHolder(enchantment).is(ModTags.TRADEABLE)) {
                        tooltipComponents.add(Component.translatable(enchantment.getDescriptionId()).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN)));
                        tooltipComponents.add(Component.translatable("tooltip.enchantery.tradeable").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN)));
                    }
                    if (EnchanteryLogic.getHolder(enchantment).is(ModTags.TREASURE)) {
                        tooltipComponents.add(Component.translatable(enchantment.getDescriptionId()).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GOLD)));
                        tooltipComponents.add(Component.translatable("tooltip.enchantery.treasure").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GOLD)));
                    }
                });
            }
        }
    }

    @Inject(method = "inventoryTick", at = @At(value = "HEAD"))
    public void inventoryTick(Level level, Entity entity, int inventorySlot, boolean isCurrentItem, CallbackInfo ci) {
        EnchanteryLogic.leechingCurseLogic(level, entity, inventorySlot);
    }

    @ModifyVariable(
            method = "hurt",
            at = @At(value = "HEAD"), index = 1, argsOnly = true)
    public int extraDamage(int amount) {
        int f = EnchantmentHelper.getItemEnchantmentLevel(ModEnchants.DIMINISHING_CURSE.get(), (ItemStack)(Object) this);
        if (f > 0) amount = amount + f;
        return amount;
    }
}
