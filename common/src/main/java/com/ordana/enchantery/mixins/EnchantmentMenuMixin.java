package com.ordana.enchantery.mixins;

import com.google.common.collect.Lists;
import com.ordana.enchantery.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Mixin(EnchantmentMenu.class)
public abstract class EnchantmentMenuMixin extends AbstractContainerMenu {

    @Shadow @Final private RandomSource random;

    @Shadow
    private final ContainerLevelAccess access;

    protected EnchantmentMenuMixin(@Nullable MenuType<?> menuType, int i, ContainerLevelAccess containerLevelAccess) {
        super(menuType, i);
        this.access = containerLevelAccess;
    }

    private int getCurseAugments() {
        AtomicInteger skulls = new AtomicInteger();

        this.access.execute((level, blockPos) -> {
            for (BlockPos blockPos2 : EnchantmentTableBlock.BOOKSHELF_OFFSETS) {
                BlockPos pos = blockPos2.offset(blockPos);
                if (level.getBlockState(pos).is(ModTags.CURSE_AUGMENTS)) {
                    skulls.set(skulls.get() + 1);
                }
            }
        });

        return skulls.get();
    }

    private int getEnchantmentStabilizers() {
        AtomicInteger candles = new AtomicInteger();

        this.access.execute((level, blockPos) -> {
            for (BlockPos blockPos2 : EnchantmentTableBlock.BOOKSHELF_OFFSETS) {
                BlockPos pos = blockPos2.offset(blockPos);
                if (level.getBlockState(pos).is(BlockTags.CANDLES)) {
                    if (level.getBlockState(pos).getValue(BlockStateProperties.LIT)) candles.getAndAdd(level.getBlockState(pos).getValue(BlockStateProperties.CANDLES));
                }
                if (level.getBlockState(pos).is(ModTags.ENCHANTMENT_STABILIZERS)) {
                    candles.getAndAdd(4);
                }
            }
        });

        return candles.get();
    }


    private int getEnchantmentAugments() {
        AtomicInteger clusters = new AtomicInteger();

        this.access.execute((level, blockPos) -> {
            for (BlockPos blockPos2 : EnchantmentTableBlock.BOOKSHELF_OFFSETS) {
                BlockPos pos = blockPos2.offset(blockPos);
                if (level.getBlockState(pos).is(ModTags.ENCHANTMENT_AUGMENTS)) {
                    clusters.getAndAdd(1);
                }
            }
        });

        return clusters.get();
    }

    private List<EnchantmentInstance> getSourceBooks(ItemStack stack) {
        List<EnchantmentInstance> list = Lists.newArrayList();

        this.access.execute((level, blockPos) -> {
            for (BlockPos blockPos2 : EnchantmentTableBlock.BOOKSHELF_OFFSETS) {
                BlockPos pos = blockPos2.offset(blockPos);
                if (level.getBlockEntity(pos) instanceof BaseContainerBlockEntity barrel) {

                    for (int j = 0; j < barrel.getContainerSize(); ++j) {
                        if (barrel.getItem(j).is(Items.ENCHANTED_BOOK)) {
                            var enchList = EnchantmentHelper.getEnchantments(barrel.getItem(j));

                            for (Enchantment enchantment2 : enchList.keySet()) {
                                if (enchantment2.category.canEnchant(stack.getItem())) list.add(new EnchantmentInstance(enchantment2, (random.nextInt(enchList.get(enchantment2) + getEnchantmentAugments())) + 1));
                            }
                        }
                    }
                }
            }
        });

        return list;
    }

    private Optional<EnchantmentInstance> getRandomEnchant(RandomSource random, ItemStack stack) {
        return WeightedRandom.getRandomItem(random, getSourceBooks(stack));
    }

    @Inject(method = "getEnchantmentList",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z",
                    shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void enchListMixin(ItemStack stack, int enchantSlot, int level, CallbackInfoReturnable<List<EnchantmentInstance>> cir, List<EnchantmentInstance> list) {
        getRandomEnchant(this.random, stack).ifPresent(list::add);

        if (getCurseAugments() > 4) {
            list.add(new EnchantmentInstance(Enchantments.VANISHING_CURSE, 1));
        }
        else if (random.nextInt(16) < getEnchantmentStabilizers()) {
            for (int j = 0; j < list.size(); ++j) {
                if (list.get(j).enchantment.isCurse()) list.remove(j);
            }
        }
    }
}