package com.ordana.enchantery.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;

public class RotatingEnchantingParticle extends TextureSheetParticle {
    private final double targetX;
    private final double targetY;
    private final double targetZ;

    protected RotatingEnchantingParticle(ClientLevel clientLevel, double x, double y, double z,
                                         double targetX, double targetY, double targetZ) {
        super(clientLevel, x, y, z);
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
        this.gravity = 0;
        this.hasPhysics = false;
        this.lifetime = 10*20;
    }

    @Override
    public void tick() {
        // Calculate radius vector and distance from center
        Vec3 pos = new Vec3(x,y,z);
        Vec3 center = new Vec3(targetX,targetY,targetZ);

        double radius = pos.subtract(center).length();
        double targetRadius = 0.5;
        double tT = 5*20;


        Vec3 v;

        if (radius < targetRadius) {
            // move towards the center in a spiral motion
             v = center.subtract(pos).scale(1f/tT).add(center.subtract(pos).scale(1f/radius).scale(1/(tT * tT * 2 * Math.PI)).scale(targetRadius - radius));
        } else {
            // move in a circular path around the center
            float g = 1;
             v = center.subtract(pos).cross(new Vec3(0,1,0)).normalize().scale(Math.sqrt(g / targetRadius));
        }

        this.xd += v.x;
        this.yd += v.y;
        this.zd += v.z;

        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
        }

        this.move(this.xd, this.yd, this.zd);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
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

    public static class ProviderAgument implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public ProviderAgument(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double tableX, double tableY, double tableZ,
                                       double relativeX, double relativeY, double relativeZ) {
            var p = new RotatingEnchantingParticle(level, tableX+relativeX, tableY+relativeY, tableZ+relativeZ,
                    tableX, tableY, tableZ);
            p.pickSprite(this.sprite);
            return p;
        }
    }
}
