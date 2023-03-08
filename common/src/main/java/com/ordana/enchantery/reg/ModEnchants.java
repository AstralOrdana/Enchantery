package com.ordana.enchantery.reg;

import com.ordana.enchantery.Enchantery;
import com.ordana.enchantery.enchantments.BackbitingCurseEnchantment;
import com.ordana.enchantery.enchantments.ButterfingerCurseEnchantment;
import com.ordana.enchantery.enchantments.DiminishingCurseEnchantment;
import com.ordana.enchantery.enchantments.LeechingCurseEnchantment;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.ArrowDamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.function.Supplier;

public class ModEnchants {

    public static void init() {
    }

    public static final Supplier<Enchantment> DIMINISHING_CURSE = RegHelper.registerEnchantment(
            Enchantery.res("diminishing_curse"), () -> new DiminishingCurseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.values()));

    public static final Supplier<Enchantment> BACKBITING_CURSE = RegHelper.registerEnchantment(
            Enchantery.res("backbiting_curse"), () -> new BackbitingCurseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));

    public static final Supplier<Enchantment> BUTTERFINGER_CURSE = RegHelper.registerEnchantment(
            Enchantery.res("butterfinger_curse"), () -> new ButterfingerCurseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));

    public static final Supplier<Enchantment> LEECHING_CURSE = RegHelper.registerEnchantment(
            Enchantery.res("leeching_curse"), () -> new LeechingCurseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.values()));

}
