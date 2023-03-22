package com.ordana.enchantery.reg;

import com.ordana.enchantery.Enchantery;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class ModTags {

    //blocks
    public static final TagKey<Block> MOB_HEADS = registerBlockTag("mob_heads");
    public static final TagKey<Block> ENCHANTMENT_STABILIZERS = registerBlockTag("enchantment_stabilizers");
    public static final TagKey<Block> ENCHANTMENT_AUGMENTS = registerBlockTag("enchantment_augments");
    public static final TagKey<Block> CURSE_AUGMENTS = registerBlockTag("curse_augments");

    //enchants
    public static final TagKey<Enchantment> BASIC = registerEnchantTag("always_obtainable_from_enchanting_table");
    public static final TagKey<Enchantment> TRADEABLE = registerEnchantTag("only_obtainable_from_villager_trading");
    public static final TagKey<Enchantment> TREASURE = registerEnchantTag("treasure");

    private ModTags() {
    }

    private static TagKey<Block> registerBlockTag(String id) {
        return TagKey.create(Registry.BLOCK_REGISTRY, Enchantery.res(id));
    }

    private static TagKey<Enchantment> registerEnchantTag(String id) {
        return TagKey.create(Registry.ENCHANTMENT_REGISTRY, Enchantery.res(id));
    }
}
