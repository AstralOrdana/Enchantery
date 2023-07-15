package com.ordana.enchantery.forge;

import com.ordana.enchantery.Enchantery;
import com.ordana.enchantery.EnchanteryClient;
import com.ordana.enchantery.events.BookshelfNameRendererEvent;
import com.ordana.enchantery.loot_modifiers.LootTableInjects;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Enchantery.MOD_ID)
public class EnchanteryForge {
    public static final String MOD_ID = Enchantery.MOD_ID;

    public EnchanteryForge() {
        Enchantery.commonInit();

        if (PlatHelper.getPhysicalSide().isClient()) {
            EnchanteryClient.init();
        }

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(Enchantery::commonSetup);
    }

    /*
    @SubscribeEvent
    public void onAddLootTables(LootTableLoadEvent event) {
        LootTableInjects.onLootInject(event.getLootTableManager(), event.getName(), (b) -> event.getTable().addPool(b.build()));
    }
     */

    @SubscribeEvent
    private static void onRenderLevel(RenderLevelStageEvent event){
        if(event.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
            BookshelfNameRendererEvent.renderBookName(Minecraft.getInstance().level, Minecraft.getInstance().hitResult);
        }
    }
}

