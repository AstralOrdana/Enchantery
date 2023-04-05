package com.ordana.enchantery.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class RotatingEnchantingParticle extends TextureSheetParticle {
    private final double targetX;
    private final double targetY;
    private final double targetZ;
    private final double startX;
    private final double startY;
    private final double startZ;

    protected RotatingEnchantingParticle(ClientLevel clientLevel, double x, double y, double z,
                                         double targetX, double targetY, double targetZ) {
        super(clientLevel, x, y, z);
        this.startX = x;
        this.startY = y;
        this.startZ = z;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
        this.gravity = 0;
        this.hasPhysics = false;
        this.lifetime = 500;
    }


    public static Vec3 spiralMotion(Vec3 startPosition, Vec3 center, double radius, double angularVelocity, double time) {

        var t = (Mth.clamp(time, 0, Math.PI * 2)) / (Math.PI * 2);
        var x = Mth.lerp(t, startPosition.x, center.x + Math.cos(time) * radius);
        var y = Mth.lerp(t, startPosition.y, center.y);
        var z = Mth.lerp(t, startPosition.z, center.z + Math.sin(time) * radius);
        return new Vec3(x, y, z);

    }

    @Override
    public void tick() {
        super.tick();
        // Calculate radius vector and distance from center
        Vec3 pos = new Vec3(x, y, z);
        Vec3 startPos = new Vec3(startX, startY, startZ);
        Vec3 center = new Vec3(targetX, targetY - 0.5, targetZ);

        //double radius = pos.subtract(center).length();
        double targetRadius = 1.25;
        //double tT = 5*20;

        Vec3 v = spiralMotion(startPos, center, targetRadius, Math.PI / 180, Math.toRadians(this.age) + Math.atan2(targetZ - startZ, targetX - startX) + Math.PI);
        this.setPos(v.x, v.y, v.z);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }


    public static class ProviderCurse implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public ProviderCurse(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double tableX, double tableY, double tableZ,
                                       double relativeX, double relativeY, double relativeZ) {
            var p = new RotatingEnchantingParticle(level, tableX + relativeX, tableY + relativeY, tableZ + relativeZ,
                    tableX, tableY, tableZ);
            p.pickSprite(this.sprite);
            return p;
        }
    }

    public static class ProviderStabilizer implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public ProviderStabilizer(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double tableX, double tableY, double tableZ,
                                       double relativeX, double relativeY, double relativeZ) {
            var p = new RotatingEnchantingParticle(level, tableX + relativeX, tableY + relativeY, tableZ + relativeZ,
                    tableX, tableY, tableZ);
            p.pickSprite(this.sprite);
            return p;
        }
    }

    public static class ProviderAgument implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public ProviderAgument(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double tableX, double tableY, double tableZ,
                                       double relativeX, double relativeY, double relativeZ) {
            var p = new RotatingEnchantingParticle(level, tableX + relativeX, tableY + relativeY, tableZ + relativeZ,
                    tableX, tableY, tableZ);
            p.pickSprite(this.sprite);
            return p;
        }
    }
}
