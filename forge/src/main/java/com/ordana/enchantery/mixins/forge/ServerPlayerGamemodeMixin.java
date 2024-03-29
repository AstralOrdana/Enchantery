package com.ordana.enchantery.mixins.forge;

import com.ordana.enchantery.EnchanteryLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGamemodeMixin {

    @Shadow protected ServerLevel level;

    @Shadow @Final protected ServerPlayer player;

    @Inject(method = "destroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;playerDestroy(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/item/ItemStack;)V",
    shift =  At.Shift.BEFORE), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void deleteDrop(BlockPos pos, CallbackInfoReturnable<Boolean> cir, BlockState blockState, int i,
                            BlockEntity blockEntity, Block block, ItemStack realStack,
                            ItemStack itemStack2, boolean bl2, boolean b3) {

        if(EnchanteryLogic.devouringCurseLogic(this.player, blockState, realStack)){
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
