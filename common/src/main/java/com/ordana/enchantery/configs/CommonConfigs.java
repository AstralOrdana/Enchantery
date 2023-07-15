package com.ordana.enchantery.configs;

import com.ordana.enchantery.Enchantery;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigBuilder;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigSpec;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigType;

import java.util.function.Supplier;

public class CommonConfigs {

    public static ConfigSpec SERVER_SPEC;

    public static Supplier<Boolean> SOULBOUND_MENDING_COMPAT;
    public static Supplier<Boolean> DISABLE_ANVIL_ENCHANTING;
    public static Supplier<Boolean> DISABLE_ANVIL_COST;

    public static void init() {
        ConfigBuilder builder = ConfigBuilder.create(Enchantery.res("common"), ConfigType.COMMON);

        builder.setSynced();

        builder.push("misc");
        SOULBOUND_MENDING_COMPAT = builder.define("soulbound_mending_compat", false);
        DISABLE_ANVIL_ENCHANTING = builder.define("disable_anvil_enchanting", true);
        DISABLE_ANVIL_COST = builder.define("disable_anvil_cost", true);
        builder.pop();

        //fabric specific
        PlatHelper.getPlatform().ifFabric(() -> {

        });


        SERVER_SPEC = builder.buildAndRegister();
        SERVER_SPEC.loadFromFile();
    }
}
