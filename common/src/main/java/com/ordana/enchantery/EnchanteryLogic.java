package com.ordana.enchantery;

import com.ordana.enchantery.reg.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

//put logic here, outside of mixins
public class EnchanteryLogic {

    public static void modifyEnchantmentList(ContainerLevelAccess access, RandomSource random, ItemStack stack,
                                             List<EnchantmentInstance> list) {
        AtomicInteger aguments = new AtomicInteger();
        AtomicInteger malus = new AtomicInteger();
        AtomicInteger stabilizers = new AtomicInteger();
        Map<Enchantment, Integer> enchants = new HashMap<>();


        access.execute((level, blockPos) -> {
            for (BlockPos offset : EnchantmentTableBlock.BOOKSHELF_OFFSETS) {
                BlockPos target = offset.offset(blockPos);
                BlockState targetState = level.getBlockState(target);
                if (targetState.is(ModTags.ENCHANTMENT_AUGMENTS)) {
                    aguments.getAndAdd(1);
                } else if (targetState.is(ModTags.CURSE_AUGMENTS)) {
                    malus.set(malus.get() + 1);
                } else if (targetState.is(ModTags.ENCHANTMENT_STABILIZERS)) {
                    if (targetState.getBlock() instanceof CandleBlock && targetState.getValue(BlockStateProperties.LIT)) {
                        stabilizers.getAndAdd(targetState.getValue(BlockStateProperties.CANDLES));
                    } else {
                        stabilizers.getAndAdd(4);
                    }
                } else if (level.getBlockEntity(target) instanceof Container container) {
                    for (int j = 0; j < container.getContainerSize(); ++j) {
                        if (container.getItem(j).is(Items.ENCHANTED_BOOK)) {
                            var enchList = EnchantmentHelper.getEnchantments(container.getItem(j));
                            enchList.forEach((e, v) -> enchants.merge(e, v, Math::max));
                        }
                    }
                }
            }
        });

        List<EnchantmentInstance> bookEnchants = new ArrayList<>();

        for (var e : enchants.entrySet()) {
            Enchantment en = e.getKey();
            if (en.category.canEnchant(stack.getItem())) {
                list.add(new EnchantmentInstance(en, (random.nextInt(e.getValue() + aguments.get())) + 1));
            }
        }
        //select single enchantment
        WeightedRandom.getRandomItem(random, bookEnchants).ifPresent(list::add);

        //add curses
        int curses = malus.get() / 4;
        for (int i = 0; i < curses; i++) {
            //TODO add check for curse compatibilty (ie no binding on tools) + remove duplicate curses
            list.add(new EnchantmentInstance(CURSES.get(random.nextInt(CURSES.size())), 1));
        }

        //remove curses
        if (random.nextInt(16) < stabilizers.get()) {
            list.removeIf(e -> e.enchantment.isCurse());
        }

    }

    private static final List<Enchantment> CURSES = new ArrayList<>();

    public static void setup() {
        for (var v : Registry.ENCHANTMENT) {
            if (v.isCurse()) CURSES.add(v);
        }
    }

    public static EnchantmentInfluencer getInfluenceType(Level level, BlockPos blockPos, BlockPos blockPos2) {
        BlockState state = level.getBlockState(blockPos.offset(blockPos2));
        var i = EnchantmentInfluencer.get(state);
        if (i != null && level.isEmptyBlock(blockPos.offset(blockPos2.getX() / 2, blockPos2.getY(), blockPos2.getZ() / 2))) {
            return i;
        }
        return null;
    }

    //todo find better name
    public enum EnchantmentInfluencer {
        AGUMENT(Enchantery.COLORED_RUNE.get()),
        CURSE_AGUMENT(Enchantery.CURSE_PARTICLE.get()),
        STABILIZER(Enchantery.AMETHYST_PARTICLE.get());

        public final SimpleParticleType particle;

        EnchantmentInfluencer(SimpleParticleType simpleParticleType) {
            this.particle = simpleParticleType;
        }

        @Nullable
        public static EnchantmentInfluencer get(BlockState state) {
            if (state.is(ModTags.ENCHANTMENT_AUGMENTS)) return AGUMENT;
            if (state.is(ModTags.ENCHANTMENT_STABILIZERS)) return STABILIZER;
            if (state.is(ModTags.CURSE_AUGMENTS)) return CURSE_AGUMENT;
            return null;
        }

    }

    @SuppressWarnings("all")
    public static Holder<Enchantment> getHolder(Enchantment enchantment) {
        return Registry.ENCHANTMENT.getHolder(Registry.ENCHANTMENT.getId(enchantment)).get();
    }
}
