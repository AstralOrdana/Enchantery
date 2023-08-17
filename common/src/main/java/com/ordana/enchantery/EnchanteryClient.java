package com.ordana.enchantery;

import com.mojang.blaze3d.platform.InputConstants;
import com.ordana.enchantery.particles.RotatingEnchantingParticle;
import net.mehvahdjukaar.moonlight.api.platform.ClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class EnchanteryClient {

    public static boolean isShiftDown() {
        return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), Minecraft.getInstance().options.keyShift.key.getValue());
    }

    public static void init() {
        ClientHelper.addParticleRegistration(EnchanteryClient::registerParticles);
    }
    private static void registerParticles(ClientHelper.ParticleEvent event) {
        event.register(Enchantery.STABILIZER_PARTICLE.get(), RotatingEnchantingParticle.ProviderStabilizer::new);
        event.register(Enchantery.CURSE_PARTICLE.get(), RotatingEnchantingParticle.ProviderCurse::new);
    }

    public static void addEnchantParticles(Level level, BlockPos tablePos, BlockPos bookShelfPos) {
        var type = EnchanteryLogic.getInfluenceType(level, tablePos, bookShelfPos);
        if (type != null && level.random.nextInt(16) == 0) {
            var particle = type.particle;
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
    }
}