package com.ordana.enchantery.forge;

import com.ordana.enchantery.events.BookshelfNameRendererEvent;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderEvent {

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event){
        if(event.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
            BookshelfNameRendererEvent.renderBookName(Minecraft.getInstance().level, Minecraft.getInstance().hitResult);
        }
    }

}
