package com.ordana.enchantery.forge;

import com.ordana.enchantery.Enchantery;
import net.minecraftforge.fml.common.Mod;

@Mod(Enchantery.MOD_ID)
public class EnchanteryForge {
    public static final String MOD_ID = Enchantery.MOD_ID;

    public EnchanteryForge() {
        Enchantery.commonInit();
        /*
        if (PlatformHelper.getEnv().isClient()) {
            ModidClient.init();
        }
        */
    }
}

