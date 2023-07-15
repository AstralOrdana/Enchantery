package com.ordana.enchantery.fabric;

import com.ordana.enchantery.events.BookshelfNameRendererEvent;
import com.ordana.enchantery.loot_modifiers.LootTableInjects;
import net.fabricmc.api.ModInitializer;
import com.ordana.enchantery.Enchantery;
import com.ordana.enchantery.EnchanteryClient;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
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
