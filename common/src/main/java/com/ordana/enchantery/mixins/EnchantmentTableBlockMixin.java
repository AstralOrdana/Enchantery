package com.ordana.enchantery.mixins;

import com.ordana.enchantery.EnchanteryClient;
import com.ordana.enchantery.access.EnchantmentTableBlockEntityAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(EnchantmentTableBlock.class)
public abstract class EnchantmentTableBlockMixin extends BaseEntityBlock {

    @Shadow
    @Final
    @Mutable
    public static List<BlockPos> BOOKSHELF_OFFSETS;

    protected EnchantmentTableBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void bookshelfRange(CallbackInfo ci) {
        BOOKSHELF_OFFSETS = BlockPos.betweenClosedStream(-3, 0, -3, 3, 3, 3).filter((blockPos)
                -> Math.abs(blockPos.getX()) <= 3 || Math.abs(blockPos.getZ()) <= 3).map(BlockPos::immutable).toList();
    }

    @ModifyArg(method = "animateTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/EnchantmentTableBlock;isValidBookShelf(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Z"))
    private Level addCustomParticles(Level level, BlockPos tablePos, BlockPos bookShelfPos){
        EnchanteryClient.addEnchantParticles(level, tablePos, bookShelfPos);
        return level;
    }


    /* WIP + commented out for blanketcon
    private static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (context instanceof EntityCollisionContext c) {
            var e = c.getEntity();
            if (e instanceof ExperienceOrb) {
                return Shapes.empty();
            }
        }
        return SHAPE;
    }


    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {

        if (entity instanceof ExperienceOrb xpOrb && level.getBlockEntity(pos) instanceof EnchantmentTableBlockEntity table) {
            if ((((EnchantmentTableBlockEntityAccess)table).getCharge() + xpOrb.getValue()) < 1395) {
                ((EnchantmentTableBlockEntityAccess)table).setCharge(((EnchantmentTableBlockEntityAccess)table).getCharge() + xpOrb.getValue());
                xpOrb.discard();
                float f = level.random.nextFloat();
                level.playSound(null, pos, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, f * 0.9F, (f + 1F) / 2 );
            }
        }
    }

    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (level instanceof ServerLevel serverLevel && level.getBlockEntity(pos) instanceof EnchantmentTableBlockEntity selfTile) {
            if (level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
                ExperienceOrb.award(serverLevel, Vec3.atCenterOf(pos), ((EnchantmentTableBlockEntityAccess)selfTile).getCharge());
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }
     */

}
