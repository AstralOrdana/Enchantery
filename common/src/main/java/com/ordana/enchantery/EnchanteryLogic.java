package com.ordana.enchantery;

import com.ordana.enchantery.reg.ModEnchants;
import com.ordana.enchantery.reg.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.Container;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

//put logic here, outside of mixins
public class EnchanteryLogic {

    public static void leechingCurseLogic(Level level, Entity entity, int inventorySlot) {
        ItemStack stack = entity.getSlot(inventorySlot).get();
        int g = EnchantmentHelper.getItemEnchantmentLevel(ModEnchants.LEECHING_CURSE.get(), stack);
        if (level.isClientSide()) return;

        //Leeching Curse logic
        if (level.random.nextInt(100) == 0) {
            if (g > 0) {
                int currentDam = stack.getDamageValue();
                int maxDam = stack.getMaxDamage();
                if (currentDam > 0) {
                    if (currentDam <= (maxDam - 8)) stack.setDamageValue(currentDam - 8);
                    else stack.setDamageValue(0);
                    entity.hurt(DamageSource.MAGIC, 1f);
                }
            }
        }
    }

    public static void butterfingersCurseLogic(Level level, Entity entity, int inventorySlot, boolean isCurrentItem) {
        ItemStack stack = entity.getSlot(inventorySlot).get();
        int f = EnchantmentHelper.getItemEnchantmentLevel(ModEnchants.BUTTERFINGER_CURSE.get(), stack);
        if (level.isClientSide()) return;

        //Butterfingers Curse logic
        if (level.random.nextInt(100) == 0) {
            if (isCurrentItem) {
                if (f > 0 && entity instanceof ServerPlayer player) {
                    player.drop(true);
                    player.connection.send(new ClientboundContainerSetSlotPacket(-2, 0, player.getInventory().selected, ItemStack.EMPTY));
                }
            }
        }
    }

    public static boolean devouringCurseLogic(Player player,BlockState state, ItemStack stack) {
        int f = EnchantmentHelper.getItemEnchantmentLevel(ModEnchants.DEVOURING_CURSE.get(), stack);
        if (f > 0 && player.level.random.nextBoolean()) {
            int currentDam = stack.getDamageValue();
            if (currentDam > 0) {
                stack.setDamageValue(currentDam-1);
            }
            player.awardStat(Stats.BLOCK_MINED.get(state.getBlock()));
            player.causeFoodExhaustion(0.005F);
            return true;
        }
        else return false;
    }



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
                list.add(new EnchantmentInstance(en, random.nextInt(e.getValue() + (aguments.get() > 1 ? 1 : 0))));
            }
        }
        //select single enchantment
        WeightedRandom.getRandomItem(random, bookEnchants).ifPresent(list::add);

        //add curses
        int curses = malus.get() / 4;
        for (int i = 0; i < curses; i++) {
            //TODO add check for curse compatibilty (ie no binding on tools) + remove duplicate curses
            var curse = CURSES.get(random.nextInt(CURSES.size()));
            if (curse.category.canEnchant(stack.getItem())) list.add(new EnchantmentInstance(curse, 1));
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
        AGUMENT(Enchantery.AMETHYST_PARTICLE.get()),
        CURSE_AGUMENT(Enchantery.CURSE_PARTICLE.get()),
        STABILIZER(Enchantery.STABILIZER_PARTICLE.get());

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
