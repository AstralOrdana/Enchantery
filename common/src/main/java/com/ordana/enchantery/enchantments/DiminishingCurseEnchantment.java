package com.ordana.enchantery.enchantments;

import com.ordana.enchantery.EnchanteryLogic;
import com.ordana.enchantery.reg.ModEnchants;
import com.ordana.enchantery.reg.ModTags;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Map;

public class DiminishingCurseEnchantment extends Enchantment {
    public DiminishingCurseEnchantment(Rarity rarity, EquipmentSlot... equipmentSlots) {
        super(rarity, EnchantmentCategory.ARMOR, equipmentSlots);
    }

    public int getMinCost(int level) {
        return 25;
    }

    public int getMaxCost(int level) {
        return 50;
    }

    public int getMaxLevel() {
        return 1;
    }

    public boolean canEnchant(ItemStack stack) {
        return stack.isDamageableItem() || super.canEnchant(stack);
    }

    public static boolean shouldIgnoreDurabilityDrop(ItemStack stack, int level, RandomSource random) {
        if (stack.getItem() instanceof ArmorItem && random.nextFloat() < 0.6F) {
            return false;
        } else {
            return random.nextInt(level + 1) > 0;
        }
    }

    public void doPostHurt(LivingEntity target, Entity attacker, int level) {
        RandomSource random = target.getRandom();
        Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.getRandomItemWith(ModEnchants.DIMINISHING_CURSE.get(), target);
        //if (random.nextFloat() < 0.15F) {
            if (entry != null) {
                entry.getValue().hurtAndBreak(2, target, (arg) -> arg.broadcastBreakEvent(entry.getKey()));
            }
        //}
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
}
