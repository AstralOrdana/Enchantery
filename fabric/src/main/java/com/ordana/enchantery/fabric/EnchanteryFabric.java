package com.ordana.enchantery.fabric;

import com.ordana.enchantery.Enchantery;
import com.ordana.enchantery.EnchanteryClient;
import com.ordana.enchantery.events.BookshelfNameRendererEvent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
import net.mehvahdjukaar.moonlight.fabric.MLFabricSetupCallbacks;
import net.minecraft.client.Minecraft;

public class EnchanteryFabric implements ModInitializer {

    @Override
    public void onInitialize() {

        Enchantery.commonInit();

        if (PlatHelper.getPhysicalSide().isClient()) {
            MLFabricSetupCallbacks.CLIENT_SETUP.add(EnchanteryClient::init);
        }

        MLFabricSetupCallbacks.COMMON_SETUP.add(Enchantery::commonSetup);

        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            BookshelfNameRendererEvent.renderBookName(Minecraft.getInstance().level, Minecraft.getInstance().hitResult);
        });
    }
}
