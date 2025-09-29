package com.ordana.enchantery.mixins.forge;

import com.ordana.enchantery.EnchanteryLogic;
import com.ordana.enchantery.reg.ModTags;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(VillagerTrades.EnchantBookForEmeralds.class)
public class VillagerTradesMixin {

    @ModifyVariable(method = "getOffer", at = @At("STORE"))
    private List<Enchantment> modify(List<Enchantment> original) {
        return original.stream().filter(enchantment -> EnchanteryLogic.getHolder(enchantment).is(ModTags.TRADEABLE)).toList();
    }
}