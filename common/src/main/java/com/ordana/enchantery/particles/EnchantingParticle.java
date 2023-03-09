package com.ordana.enchantery.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class EnchantingParticle extends TextureSheetParticle {
    protected EnchantingParticle(ClientLevel clientLevel, double d, double e, double f) {
        super(clientLevel, d, e, f);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return null;
    }


    public static class ProviderCurse extends EnchantmentTableParticle.Provider {

        public ProviderCurse(SpriteSet spriteSet) {
            super(spriteSet);
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            var p = super.createParticle(type, level, x, y, z, xSpeed, ySpeed, zSpeed);
            //p.setColor(1, 0, 1);
            p.scale(2);

            return p;
        }
    }

    public static class ProviderStabilizer extends EnchantmentTableParticle.Provider {

        public ProviderStabilizer(SpriteSet spriteSet) {
            super(spriteSet);
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            var p = super.createParticle(type, level, x, y, z, xSpeed, ySpeed, zSpeed);
            //p.setColor(1, 0, 1);
            p.scale(2);

            return p;
        }
    }

    public static class ProviderAgument extends EnchantmentTableParticle.Provider {

        public ProviderAgument(SpriteSet spriteSet) {
            super(spriteSet);
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            var p = super.createParticle(type, level, x, y, z, xSpeed, ySpeed, zSpeed);
            p.setColor(1, 0, 1);
            //p.scale(4);

            return p;
        }
    }
}
