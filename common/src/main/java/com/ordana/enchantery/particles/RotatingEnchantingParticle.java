package com.ordana.enchantery.particles;

import io.netty.util.internal.MathUtil;
import net.minecraft.Util;
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
    private final double toCenterAngle;

    protected RotatingEnchantingParticle(ClientLevel clientLevel, double x, double y, double z,
                                         double targetX, double targetY, double targetZ) {
        super(clientLevel, x, y, z);
        this.startX = x;
        this.startY = y;
        this.startZ = z;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
        this.toCenterAngle = (Math.atan2(targetZ - startZ, targetX - startX) + Math.PI / 2);
        this.gravity = 0;
        this.hasPhysics = false;
        this.lifetime = 500;
    }


    @Override
    public void tick() {
        super.tick();

        Vec3 startPos = new Vec3(startX, startY, startZ);
        Vec3 center = new Vec3(targetX, targetY - 0.5, targetZ);
        double targetRadius = 1.25;


        var t = Mth.clamp(Math.toRadians(age) / (Math.PI * 2), 0, 1);

        var time = (Math.toRadians(age) / t) + (toCenterAngle);

        var x = Mth.lerp(t, startPos.x, center.x + Math.cos(time) * -1 * targetRadius);
        var y = Mth.lerp(t, startPos.y, center.y);
        var z = Mth.lerp(t, startPos.z, center.z + Math.sin(time) * -1 * targetRadius);

        this.setPos(x, y, z);
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
