package com.ordana.enchantery.enchantments;

import com.ordana.enchantery.EnchanteryLogic;
import com.ordana.enchantery.reg.ModTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;

public class ImprecisionCurseEnchantment extends Enchantment {
    public ImprecisionCurseEnchantment(Rarity rarity, EquipmentSlot... equipmentSlots) {
        super(rarity, EnchantmentCategory.BOW, equipmentSlots);
    }

    public int getMinCost(int level) {
        return 25;
    }

    public int getMaxCost(int level) {
        return 50;
    }

    public int getMaxLevel() {
        return 3;
    }

    public boolean canEnchant(ItemStack stack) {
        return super.canEnchant(stack);
    }

    @Override
    public boolean isTreasureOnly() {
        return !EnchanteryLogic.getHolder(this).is(ModTags.BASIC);
    }

    @Override
    public boolean isTradeable() {
        return EnchanteryLogic.getHolder(this).is(ModTags.TRADEABLE);
    }

    @Override
    public boolean isDiscoverable() {
        return EnchanteryLogic.getHolder(this).is(ModTags.BASIC);
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    public boolean checkCompatibility(Enchantment other) {
        return super.checkCompatibility(other);
    }
}
