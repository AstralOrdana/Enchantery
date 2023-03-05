package com.ordana.enchantery.fabric;

import com.ordana.enchantery.LootTableInjects;
import net.fabricmc.api.ModInitializer;
import com.ordana.enchantery.Enchantery;
import com.ordana.enchantery.EnchanteryClient;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.mehvahdjukaar.moonlight.api.platform.PlatformHelper;
import net.mehvahdjukaar.moonlight.fabric.FabricSetupCallbacks;

public class EnchanteryFabric implements ModInitializer {

    @Override
    public void onInitialize() {

        Enchantery.commonInit();

        LootTableEvents.MODIFY.register((m, t, r, b, s) -> LootTableInjects.onLootInject(t, r, b::withPool));

        if (PlatformHelper.getEnv().isClient()) {
            FabricSetupCallbacks.CLIENT_SETUP.add(EnchanteryClient::init);
        }
    }
}
