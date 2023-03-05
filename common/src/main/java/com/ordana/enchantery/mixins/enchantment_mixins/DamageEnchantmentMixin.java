package com.ordana.enchantery.mixins.enchantment_mixins;

import com.ordana.enchantery.ModTags;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DamageEnchantment.class)
public abstract class DamageEnchantmentMixin extends Enchantment {
    protected DamageEnchantmentMixin(Rarity rarity, int i, EquipmentSlot... equipmentSlots) {
        super(rarity, EnchantmentCategory.WEAPON, equipmentSlots);
        this.type = i;
    }

    @Shadow
    public final int type;

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
