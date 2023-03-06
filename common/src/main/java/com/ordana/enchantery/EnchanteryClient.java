package com.ordana.enchantery;

import net.mehvahdjukaar.moonlight.api.platform.ClientPlatformHelper;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.EnchantmentTableParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class EnchanteryClient {

    public static void init() {
        ClientConfigs.init();
        ClientPlatformHelper.addParticleRegistration(EnchanteryClient::registerParticles);
    }

    private static void registerParticles(ClientPlatformHelper.ParticleEvent event) {
        event.register(Enchantery.COLORED_RUNE.get(), Provider::new);
        event.register(Enchantery.AMETHYST_PARTICLE.get(), Provider::new);
        event.register(Enchantery.FLAME_PARTICLE.get(), Provider::new);
        event.register(Enchantery.CURSE_PARTICLE.get(), Provider::new);
    }

    public static void addEnchantParticles(Level level, BlockPos pos, BlockPos target) {
        if(ClientConfigs.COLORED_PARTICLES.get()) {
            var type = EnchanteryLogic.getInfluenceType(level, pos, target);
            if (type == EnchanteryLogic.EnchantmentInfluencer.STABILIZER) {
                RandomSource random = level.random;
                var particle = Enchantery.AMETHYST_PARTICLE.get();
                if (level.getBlockState(pos).is(BlockTags.CANDLES)) particle = ParticleTypes.FLAME;
                level.addParticle(
                        particle,
                        pos.getX() + 0.5,
                        pos.getY() + 2.0,
                        pos.getZ() + 0.5,
                        (target.getX() + random.nextFloat()) - 0.5,
                        (target.getY() - random.nextFloat() - 1.0F),
                        (target.getZ() + random.nextFloat()) - 0.5
                );
            }
        }
    }


    private static class Provider extends EnchantmentTableParticle.Provider {

        public Provider(SpriteSet spriteSet) {
            super(spriteSet);
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            var p = super.createParticle(type, level, x, y, z, xSpeed, ySpeed, zSpeed);
            //p.setColor(1, 0, 1);
            p.scale(4);

            return p;
        }
    }
}