package com.ordana.enchantery;

import com.ordana.enchantery.loot_modifiers.LootTableOverrides;
import com.ordana.enchantery.reg.ModEnchants;
import net.mehvahdjukaar.moonlight.api.misc.RegSupplier;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Enchantery {

    public static final String MOD_ID = "enchantery";
    public static final Logger LOGGER = LogManager.getLogger();

    public static ResourceLocation res(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    public static void commonInit() {
        LootTableOverrides.INSTANCE.register();
        ModEnchants.init();

    }

    public static void commonSetup(){
        EnchanteryLogic.setup();
    }

    public static final RegSupplier<SimpleParticleType> COLORED_RUNE = RegHelper.registerParticle(res("colored_rune"));
    public static final RegSupplier<SimpleParticleType> AMETHYST_PARTICLE = RegHelper.registerParticle(res("amethyst_particle"));
    public static final RegSupplier<SimpleParticleType> CURSE_PARTICLE = RegHelper.registerParticle(res("curse_particle"));
    public static final RegSupplier<SimpleParticleType> FLAME_PARTICLE = RegHelper.registerParticle(res("flame_particle"));

}