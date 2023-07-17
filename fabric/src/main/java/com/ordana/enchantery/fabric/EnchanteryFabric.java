package com.ordana.enchantery.fabric;

import com.ordana.enchantery.Enchantery;
import com.ordana.enchantery.EnchanteryClient;
import net.fabricmc.api.ModInitializer;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
import net.mehvahdjukaar.moonlight.fabric.MLFabricSetupCallbacks;

public class EnchanteryFabric implements ModInitializer {

    @Override
    public void onInitialize() {

        Enchantery.commonInit();

        if (PlatHelper.getPhysicalSide().isClient()) {
            MLFabricSetupCallbacks.CLIENT_SETUP.add(EnchanteryClient::init);
            RenderEvent.init();

        }

        MLFabricSetupCallbacks.COMMON_SETUP.add(Enchantery::commonSetup);
    }
}
