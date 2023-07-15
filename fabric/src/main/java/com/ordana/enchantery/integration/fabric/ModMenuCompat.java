package com.ordana.enchantery.integration.fabric;

import com.ordana.enchantery.Enchantery;
import com.ordana.enchantery.configs.CommonConfigs;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.ordana.enchantery.configs.ClientConfigs;
import net.mehvahdjukaar.moonlight.api.platform.configs.fabric.FabricConfigListScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;

import java.awt.*;

public class ModMenuCompat implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ModConfigScreen::new;
    }

    private static class ModConfigScreen extends FabricConfigListScreen {

        public ModConfigScreen(Screen parent) {
            super(Enchantery.MOD_ID, Blocks.ENCHANTING_TABLE.asItem().getDefaultInstance(),
                    Component.literal("\u00A76Enchantery Configs"),
                    new ResourceLocation("minecraft","textures/block/bookshelf.png"),
                    parent, ClientConfigs.CLIENT_SPEC, CommonConfigs.SERVER_SPEC);
        }

        @Override
        protected void addExtraButtons() {

            int y = this.height - 27;
            int centerX = this.width / 2;

            //this.addRenderableWidget(new Button(centerX - 45, y, 90, 20, CommonComponents.GUI_BACK, (button) -> this.minecraft.setScreen(this.parent)));

            //TODO: link buttons

        }

    }

}