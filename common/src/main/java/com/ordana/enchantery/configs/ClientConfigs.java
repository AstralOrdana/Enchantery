package com.ordana.enchantery.configs;

import com.ordana.enchantery.Enchantery;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigBuilder;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigSpec;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigType;

import java.util.function.Supplier;

public class ClientConfigs {

    public static void init() {
    }

    public static final ConfigSpec CONFIG_SPEC;

    public static final Supplier<Boolean> ENCHANTING_PARTICLES;
    public static final Supplier<Integer> INTEGER_CONFIG;

    static {
        ConfigBuilder builder = ConfigBuilder.create(Enchantery.res("client"), ConfigType.CLIENT);

        builder.push("general");
        ENCHANTING_PARTICLES = builder.comment("Creates colored runes particles to show enchant affecting blocks")
                .define("colored_runes", true);

        INTEGER_CONFIG = builder.comment("Integer Config Name").define("integer_config", 16, 8, 512);
        builder.pop();

        CONFIG_SPEC = builder.buildAndRegister();
        CONFIG_SPEC.loadFromFile();
    }

}