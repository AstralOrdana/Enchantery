package com.ordana.enchantery.forge;

import com.ordana.enchantery.Enchantery;
import com.ordana.enchantery.EnchanteryClient;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
import net.minecraftforge.common.MinecraftForge;
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
            MinecraftForge.EVENT_BUS.register(RenderEvent.class);
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

}

