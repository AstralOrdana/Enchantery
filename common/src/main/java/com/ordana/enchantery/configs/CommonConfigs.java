package com.ordana.enchantery.configs;

import com.ordana.enchantery.Enchantery;
import net.mehvahdjukaar.moonlight.api.platform.PlatformHelper;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigBuilder;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigSpec;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigType;

import java.util.function.Supplier;

public class CommonConfigs {

    public static final ConfigSpec SERVER_SPEC;

    public static final Supplier<Boolean> SOULBOUND_MENDING_COMPAT;
    static {
        ConfigBuilder builder = ConfigBuilder.create(Enchantery.res("common"), ConfigType.COMMON);

        builder.setSynced();

        builder.push("misc");
        SOULBOUND_MENDING_COMPAT = builder.define("soulbound_mending_compat", false);
        builder.pop();

        //fabric specific
        PlatformHelper.getPlatform().ifFabric(() -> {

        });


        SERVER_SPEC = builder.buildAndRegister();
        SERVER_SPEC.loadFromFile();
    }

    public static void bump() {
        // Literally just a way to ensure for the class to be loaded
    }
}
