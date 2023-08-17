package com.ordana.enchantery;

import com.ordana.enchantery.configs.ClientConfigs;
import com.ordana.enchantery.configs.CommonConfigs;
import com.ordana.enchantery.loot_modifiers.LootTableOverrides;
import com.ordana.enchantery.reg.ModEnchants;
import net.mehvahdjukaar.moonlight.api.events.IDropItemOnDeathEvent;
import net.mehvahdjukaar.moonlight.api.events.MoonlightEventsHelper;
import net.mehvahdjukaar.moonlight.api.misc.RegSupplier;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
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

        CommonConfigs.init();
        if(PlatHelper.getPhysicalSide().isClient()){
            ClientConfigs.init();
        }

        MoonlightEventsHelper.addListener(EnchanteryLogic::soulboundLogic, IDropItemOnDeathEvent.class);
    }


    public static void commonSetup(){
        EnchanteryLogic.setup();
    }

    public static final RegSupplier<SimpleParticleType> CURSE_PARTICLE = RegHelper.registerParticle(res("curse_particle"));
    public static final RegSupplier<SimpleParticleType> STABILIZER_PARTICLE = RegHelper.registerParticle(res("stabilizer_particle"));

}