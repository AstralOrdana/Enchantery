package com.ordana.enchantery.mixins;

import com.ordana.enchantery.EnchanteryClient;
import com.ordana.enchantery.EnchanteryLogic;
import com.ordana.enchantery.configs.ClientConfigs;
import com.ordana.enchantery.reg.ModEnchants;
import com.ordana.enchantery.reg.ModTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
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
public class TooltipMixin {

    @Inject(method = "appendEnchantmentNames", at = @At(value = "TAIL"))
    private static void enchantmentTooltips(List<Component> tooltipComponents, ListTag storedEnchantments, CallbackInfo ci) {

        if (!ClientConfigs.ENABLE_TOOLTIPS.get() || storedEnchantments.size() < 1) {
            return;
        }

        if (!EnchanteryClient.isShiftDown())
            tooltipComponents.add(Component.literal("[+]").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN)));

        else for (int i = 0; i < storedEnchantments.size(); ++i) {
            CompoundTag compoundTag = storedEnchantments.getCompound(i);
            BuiltInRegistries.ENCHANTMENT.getOptional(EnchantmentHelper.getEnchantmentId(compoundTag)).ifPresent((enchantment) -> {
                var holder = EnchanteryLogic.getHolder(enchantment);
                var desc = enchantment.getDescriptionId();

                if (holder.is(ModTags.EXEMPT)) tooltipComponents.add(Component.translatable("tooltip.enchantery.exempt").setStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_GRAY)));

                if (holder.is(ModTags.BASIC)) {
                    tooltipComponents.add(Component.translatable(desc).setStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_PURPLE)));
                    tooltipComponents.add(Component.translatable("tooltip.enchantery.basic").setStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_PURPLE)));
                }
                if (holder.is(ModTags.TRADEABLE)) {
                    tooltipComponents.add(Component.translatable(desc).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN)));
                    tooltipComponents.add(Component.translatable("tooltip.enchantery.tradeable").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN)));
                }
                if (holder.is(ModTags.TREASURE)) {
                    tooltipComponents.add(Component.translatable(desc).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GOLD)));
                    tooltipComponents.add(Component.translatable("tooltip.enchantery.treasure").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GOLD)));
                }
                if (!holder.is(ModTags.TREASURE) && !holder.is(ModTags.TRADEABLE) && !holder.is(ModTags.BASIC)) {
                    tooltipComponents.add(Component.translatable(desc).setStyle(Style.EMPTY.applyFormat(ChatFormatting.RED)));
                    tooltipComponents.add(Component.translatable("tooltip.enchantery.untagged1").setStyle(Style.EMPTY.applyFormat(ChatFormatting.RED)));
                    tooltipComponents.add(Component.translatable("tooltip.enchantery.untagged2").setStyle(Style.EMPTY.applyFormat(ChatFormatting.RED)));
                    tooltipComponents.add(Component.translatable("tooltip.enchantery.untagged3").setStyle(Style.EMPTY.applyFormat(ChatFormatting.RED)));
                }

            });
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