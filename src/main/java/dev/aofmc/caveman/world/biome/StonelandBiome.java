package dev.aofmc.caveman.world.biome;

import dev.aofmc.caveman.Caveman;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

public class StonelandBiome extends Biome {
    public StonelandBiome() {
        super(new Settings()
            .category(Biome.Category.PLAINS)
            .configureSurfaceBuilder(SurfaceBuilder.NOPE, SurfaceBuilder.STONE_CONFIG)
            .depth(-300)
            .scale(-300)
            .downfall(0)
            .effects(new BiomeEffects.Builder()
                .waterColor(4159204)
                .waterFogColor(329011)
                .fogColor(12638463)
                .build()
            )
            .noises(ImmutableList.of(new Biome.MixedNoisePoint(
                0.0F,
                0.0F,
                0.0F,
                0.0F,
                1.0F
            )))
            .parent(null)
            .precipitation(Precipitation.NONE)
            .temperature(0.8F)
        );

        this.addStructureFeature(Caveman.MINI_DUNGEON_FEATURE.configure(FeatureConfig.DEFAULT));

        this.addStructureFeature(Caveman.MACRO_DUNGEON_FEATURE.configure(FeatureConfig.DEFAULT));

        this.addStructureFeature(DefaultBiomeFeatures.STRONGHOLD);

        DefaultBiomeFeatures.addFossils(this);
        DefaultBiomeFeatures.addMineables(this);

        Biomes.PLAINS
            .getEntitySpawnList(SpawnGroup.MONSTER)
            .forEach(spawnEntry -> this.addSpawn(SpawnGroup.MONSTER, spawnEntry));
    }

}
