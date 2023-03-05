package com.ordana.enchantery.mixins.enchantment_mixins;

import com.ordana.enchantery.ModTags;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.ArrowDamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.TridentImpalerEnchantment;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TridentImpalerEnchantment.class)
public abstract class TridentImpalerEnchantmentMixin extends Enchantment {
    protected TridentImpalerEnchantmentMixin(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] equipmentSlots) {
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
