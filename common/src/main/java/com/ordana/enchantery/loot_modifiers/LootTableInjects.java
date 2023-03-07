package com.ordana.enchantery.loot_modifiers;

import com.ordana.enchantery.Enchantery;
import net.mehvahdjukaar.moonlight.api.platform.ForgeHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;

import java.util.List;
import java.util.function.Consumer;

public class LootTableInjects {

    public static void onLootInject(LootTables lootManager, ResourceLocation name, Consumer<LootPool.Builder> builderConsumer) {

        var lootChestInjects = List.of(
                "bastion_treasure",
                "bastion_bridge",
                "end_city_treasure",
                "nether_bridge",
                "ruined_portal"
        );


        for (var table : lootChestInjects) {
            if (name.equals(new ResourceLocation("minecraft", "chests/" + table))) {
                {
                    LootPool.Builder pool = LootPool.lootPool();
                    pool.add(LootTableReference.lootTableReference(Enchantery.res("injects/" + table)));
                    ForgeHelper.setPoolName(pool, "spelunkery_" + table);
                    builderConsumer.accept(pool);
                }
            }
        }
    }
}
