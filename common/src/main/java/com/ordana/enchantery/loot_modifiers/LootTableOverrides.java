package com.ordana.enchantery.loot_modifiers;

import com.google.gson.JsonElement;
import com.ordana.enchantery.Enchantery;
import net.mehvahdjukaar.moonlight.api.resources.RPUtils;
import net.mehvahdjukaar.moonlight.api.resources.ResType;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynServerResourcesProvider;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynamicDataPack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class LootTableOverrides extends DynServerResourcesProvider {

    public static final LootTableOverrides INSTANCE = new LootTableOverrides();

    public LootTableOverrides() {
        super(new DynamicDataPack(Enchantery.res("generated_pack"), Pack.Position.TOP, true, true));
        this.dynamicPack.generateDebugResources = true;
        this.dynamicPack.addNamespaces("minecraft");
    }

    @Override
    public Logger getLogger() {
        return Enchantery.LOGGER;
    }

    @Override
    public boolean dependsOnLoadedPacks() {
        return true;
    }

    public void overrideDataFile(ResourceManager manager, List list, String targetNamespace, String targetPath, String sourcePath, ResType resType) {
        for (var recipe : list) {
            ResourceLocation target = new ResourceLocation(targetNamespace, targetPath + recipe);
            ResourceLocation source = new ResourceLocation("enchantery", sourcePath + recipe + ".json");

            try (var bsStream = manager.getResource(source).orElseThrow().open()) {
                JsonElement bsElement = RPUtils.deserializeJson(bsStream);
                dynamicPack.addJson(target, bsElement, resType);

            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public void regenerateDynamicAssets(ResourceManager manager) {

        var lootChestOverrides = List.of(
                "abandoned_mineshaft",
                "ancient_city",
                "desert_pyramid",
                "jungle_temple",
                "pillager_outpost",
                "simple_dungeon",
                "stronghold_corridor",
                "stronghold_crossing",
                "stronghold_library",
                "underwater_ruin_big",
                "woodland_mansion"
        );

        overrideDataFile(manager, lootChestOverrides,
                "minecraft", "chests/",
                "overrides/loot_tables/", ResType.LOOT_TABLES);

        var lootGameplayOverrides = List.of(
                "piglin_bartering"
        );

        overrideDataFile(manager, lootGameplayOverrides,
                "minecraft", "gameplay/",
                "overrides/loot_tables/", ResType.LOOT_TABLES);
    }
}
