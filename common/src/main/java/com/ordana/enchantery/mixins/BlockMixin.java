package com.ordana.enchantery.mixins;

import com.ordana.enchantery.EnchanteryLogic;
import com.ordana.enchantery.reg.ModEnchants;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Supplier;

@Mixin(Block.class)
public class BlockMixin {

    @Inject(method = "playerDestroy", at = @At(value = "HEAD"), cancellable = true)
    private void deleteDrop(Level level, Player player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack tool, CallbackInfo cir) {
        if(EnchanteryLogic.devouringCurseLogic(level, tool)) cir.cancel();
    }
}
