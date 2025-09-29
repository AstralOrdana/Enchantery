package com.ordana.enchantery.reg;

import com.ordana.enchantery.Enchantery;
import net.mehvahdjukaar.moonlight.api.misc.RegSupplier;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.core.particles.SimpleParticleType;

public class ModParticles {
    public static void init() {
    }

    public static final RegSupplier<SimpleParticleType> CURSE_PARTICLE = RegHelper.registerParticle(Enchantery.res("curse_particle"));
    public static final RegSupplier<SimpleParticleType> STABILIZER_PARTICLE = RegHelper.registerParticle(Enchantery.res("stabilizer_particle"));
}
