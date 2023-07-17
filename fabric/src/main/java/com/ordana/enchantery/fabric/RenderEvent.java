package com.ordana.enchantery.fabric;

import com.mojang.blaze3d.platform.InputConstants;
import com.ordana.enchantery.events.BookshelfNameRendererEvent;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Minecraft;

public class RenderEvent {

    public static void init() {
        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            BookshelfNameRendererEvent.renderBookName(Minecraft.getInstance().level, Minecraft.getInstance().hitResult);
        });
    }

}
