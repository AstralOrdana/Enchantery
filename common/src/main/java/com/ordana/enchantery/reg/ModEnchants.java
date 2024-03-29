package com.ordana.enchantery.reg;

import com.ordana.enchantery.Enchantery;
import com.ordana.enchantery.enchantments.*;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.function.Supplier;

public class ModEnchants {

    public static void init() {
    }

    public static final Supplier<Enchantment> SOULBOUND = RegHelper.registerEnchantment(
            Enchantery.res("soulbound"), () -> new SoulboundEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.values()));

    public static final Supplier<Enchantment> DIMINISHING_CURSE = RegHelper.registerEnchantment(
            Enchantery.res("diminishing_curse"), () -> new DiminishingCurseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.values()));

    public static final Supplier<Enchantment> BACKBITING_CURSE = RegHelper.registerEnchantment(
            Enchantery.res("backbiting_curse"), () -> new BackbitingCurseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));

    public static final Supplier<Enchantment> BUTTERFINGER_CURSE = RegHelper.registerEnchantment(
            Enchantery.res("butterfinger_curse"), () -> new ButterfingerCurseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));

    public static final Supplier<Enchantment> LEECHING_CURSE = RegHelper.registerEnchantment(
            Enchantery.res("leeching_curse"), () -> new LeechingCurseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.values()));

    public static final Supplier<Enchantment> DEVOURING_CURSE = RegHelper.registerEnchantment(
            Enchantery.res("devouring_curse"), () -> new DevouringCurseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));

    public static final Supplier<Enchantment> IMPRECISION_CURSE = RegHelper.registerEnchantment(
            Enchantery.res("imprecision_curse"), () -> new ImprecisionCurseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));

    public static final Supplier<Enchantment> KICKBACK_CURSE = RegHelper.registerEnchantment(
            Enchantery.res("kickback_curse"), () -> new KickbackCurseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));

    public static final Supplier<Enchantment> DIFFUSION_CURSE = RegHelper.registerEnchantment(
            Enchantery.res("diffusion_curse"), () -> new DiffusionCurseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));

}
