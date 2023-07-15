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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
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

        MoonlightEventsHelper.addListener(Enchantery::soulboundLogic, IDropItemOnDeathEvent.class);
    }

    private static void soulboundLogic(IDropItemOnDeathEvent event) {
        ItemStack stack = event.getItemStack();
        int f = EnchantmentHelper.getItemEnchantmentLevel(ModEnchants.SOULBOUND.get(), stack);
        if (f > 0) {
            if(event.isBeforeDrop()) {
                int maxDam = stack.getMaxDamage();
                int currentDam = stack.getDamageValue();
                int dam = maxDam - ((maxDam - currentDam) / 2);
                stack.setDamageValue(dam - 1);
            }
            event.setCanceled(true);
        }
    }

    public static void commonSetup(){
        EnchanteryLogic.setup();
    }

    public static final RegSupplier<SimpleParticleType> COLORED_RUNE = RegHelper.registerParticle(res("colored_rune"));
    public static final RegSupplier<SimpleParticleType> AMETHYST_PARTICLE = RegHelper.registerParticle(res("amethyst_particle"));
    public static final RegSupplier<SimpleParticleType> CURSE_PARTICLE = RegHelper.registerParticle(res("curse_particle"));
    public static final RegSupplier<SimpleParticleType> STABILIZER_PARTICLE = RegHelper.registerParticle(res("stabilizer_particle"));

}