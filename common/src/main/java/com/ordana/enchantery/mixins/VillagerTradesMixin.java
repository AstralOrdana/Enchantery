package com.ordana.enchantery.mixins;

import com.ordana.enchantery.EnchanteryLogic;
import com.ordana.enchantery.reg.ModTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.npc.VillagerTrades;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Predicate;
import java.util.stream.Stream;

@Mixin(VillagerTrades.EnchantBookForEmeralds.class)
public class VillagerTradesMixin {

    @Redirect(
            method = "getOffer",
            at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;"))
    public Stream tagList(Stream instance, Predicate predicate) {

        return BuiltInRegistries.ENCHANTMENT.stream().filter(enchantment -> EnchanteryLogic.getHolder(enchantment).is(ModTags.TRADEABLE));
    }

}