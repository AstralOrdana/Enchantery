package com.ordana.enchantery.mixins;

import com.ordana.enchantery.EnchanteryClient;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
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

    @Inject(method = "animateTick", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/util/RandomSource;nextInt(I)I",
            shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void addCustomParticles(BlockState state, Level level, BlockPos tablePos, RandomSource random, CallbackInfo ci, Iterator var5, BlockPos bookShelfPos){
        EnchanteryClient.addEnchantParticles(level, tablePos, bookShelfPos);
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
