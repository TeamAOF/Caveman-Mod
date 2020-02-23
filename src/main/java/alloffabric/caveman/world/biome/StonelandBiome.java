package alloffabric.caveman.world.biome;

import alloffabric.caveman.Caveman;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityCategory;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.MineshaftFeature;
import net.minecraft.world.gen.feature.MineshaftFeatureConfig;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

public class StonelandBiome extends Biome {
    public StonelandBiome() {
        super(new Settings()
            .category(Biome.Category.PLAINS)
            .configureSurfaceBuilder(SurfaceBuilder.NOPE, SurfaceBuilder.STONE_CONFIG)
            .depth(0.125F)
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
            .scale(0.05F)
            .temperature(0.8F)
        );

        this.addStructureFeature(Caveman.MINI_DUNGEON_FEATURE.configure(FeatureConfig.DEFAULT));
        this.addFeature(GenerationStep.Feature.UNDERGROUND_STRUCTURES, Caveman.MINI_DUNGEON_FEATURE
            .configure(FeatureConfig.DEFAULT)
            .createDecoratedFeature(Decorator.NOPE.configure(DecoratorConfig.DEFAULT))
        );

        this.addStructureFeature(Feature.MINESHAFT.configure(new MineshaftFeatureConfig(
            0.016 * Caveman.config.vanillaTweaks.mineshaftsCommonness,
            MineshaftFeature.Type.NORMAL
        )));
        this.addStructureFeature(Feature.STRONGHOLD.configure(FeatureConfig.DEFAULT));

        DefaultBiomeFeatures.addDefaultStructures(this);
        DefaultBiomeFeatures.addDungeons(this);
        DefaultBiomeFeatures.addFossils(this);
        DefaultBiomeFeatures.addMineables(this);

        Biomes.PLAINS
            .getEntitySpawnList(EntityCategory.MONSTER)
            .forEach(spawnEntry -> this.addSpawn(EntityCategory.MONSTER, spawnEntry));
    }

}
