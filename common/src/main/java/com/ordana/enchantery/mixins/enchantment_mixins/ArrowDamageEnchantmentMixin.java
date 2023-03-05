package com.ordana.enchantery.mixins.enchantment_mixins;

import com.google.common.collect.Lists;
import com.ordana.enchantery.ModTags;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.*;
import org.spongepowered.asm.mixin.Mixin;

import java.util.ArrayList;
import java.util.List;

@Mixin(ArrowDamageEnchantment.class)
public abstract class ArrowDamageEnchantmentMixin extends Enchantment {
    protected ArrowDamageEnchantmentMixin(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] equipmentSlots) {
        super(rarity, enchantmentCategory, equipmentSlots);
    }

    @Override
    public boolean isTreasureOnly() {
        return !Registry.ENCHANTMENT.getHolder(Registry.ENCHANTMENT.getId(this)).get().is(ModTags.BASIC);
    }

    @Override
    public boolean isTradeable() {
        return Registry.ENCHANTMENT.getHolder(Registry.ENCHANTMENT.getId(this)).get().is(ModTags.TRADEABLE);
    }

    @Override
    public boolean isDiscoverable() {
        return Registry.ENCHANTMENT.getHolder(Registry.ENCHANTMENT.getId(this)).get().is(ModTags.BASIC);
    }
}
