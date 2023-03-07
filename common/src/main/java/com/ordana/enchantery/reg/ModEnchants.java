package com.ordana.enchantery.reg;

import com.ordana.enchantery.Enchantery;
import com.ordana.enchantery.enchantments.DiminishingCurseEnchantment;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.ArrowDamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.function.Supplier;

public class ModEnchants extends Enchantment {
    protected ModEnchants(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] equipmentSlots) {
        super(rarity, enchantmentCategory, equipmentSlots);
    }

    public static void init() {
    }

    public static final Supplier<Enchantment> DIMINISHING_CURSE = RegHelper.registerEnchantment(
            Enchantery.res("diminishing_curse"), () -> new DiminishingCurseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
}
