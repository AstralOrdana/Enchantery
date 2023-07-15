package com.ordana.enchantery.configs;

import com.ordana.enchantery.Enchantery;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigBuilder;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigSpec;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigType;

import java.util.function.Supplier;

public class ClientConfigs {

    public static ConfigSpec CLIENT_SPEC;

    public static Supplier<Boolean> ENABLE_TOOLTIPS;
    public static Supplier<Boolean> BOOKSHELF_LABELS_SHIFT;

    public static void init() {
        ConfigBuilder builder = ConfigBuilder.create(Enchantery.res("client"), ConfigType.CLIENT);

        builder.push("general");
        ENABLE_TOOLTIPS = builder.comment("Enable Tooltips").define("enable_tooltips", true);
        BOOKSHELF_LABELS_SHIFT = builder.define("bookshelf_labels_shift", true);

        builder.pop();

        CLIENT_SPEC = builder.buildAndRegister();

        //load early
        CLIENT_SPEC.loadFromFile();
    }
}