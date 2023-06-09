package com.ordana.enchantery;

import com.ordana.enchantery.configs.ClientConfigs;
import com.ordana.enchantery.particles.EnchantingParticle;
import net.mehvahdjukaar.moonlight.api.platform.ClientHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

public class EnchanteryClient {

    public static void init() {
        ClientHelper.addParticleRegistration(EnchanteryClient::registerParticles);
    }


    private static void registerParticles(ClientHelper.ParticleEvent event) {
        event.register(Enchantery.COLORED_RUNE.get(), EnchantingParticle.ProviderAgument::new);
        event.register(Enchantery.AMETHYST_PARTICLE.get(), EnchantingParticle.ProviderStabilizer::new);
        event.register(Enchantery.STABILIZER_PARTICLE.get(), EnchantingParticle.ProviderStabilizer::new);
        event.register(Enchantery.CURSE_PARTICLE.get(), EnchantingParticle.ProviderCurse::new);
    }

    public static void addEnchantParticles(Level level, BlockPos tablePos, BlockPos bookShelfPos) {
        //if (ClientConfigs.ENCHANTING_PARTICLES.get()) {
            var type = EnchanteryLogic.getInfluenceType(level, tablePos, bookShelfPos);
            if (type != null) {
                RandomSource random = level.random;
                var particle = type.particle;
                //if (level.getBlockState(tablePos).is(BlockTags.CANDLES)) particle = ParticleTypes.FLAME;
                level.addParticle(
                        particle,
                        tablePos.getX() + 0.5,
                        tablePos.getY() + 2.0,
                        tablePos.getZ() + 0.5,
                        bookShelfPos.getX(),
                        bookShelfPos.getY() - 1,
                        bookShelfPos.getZ()
                );
            }
        //}
    }


}