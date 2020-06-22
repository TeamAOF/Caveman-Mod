package dev.aofmc.caveman.world.biome;

import dev.aofmc.caveman.Caveman;
import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountDecoratorConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

public class StonelandDeadForestBiome extends Biome {
    private static final RandomPatchFeatureConfig CONFIG = new RandomPatchFeatureConfig.Builder(
            new SimpleBlockStateProvider(Blocks.GRASS.getDefaultState()
            ),
            new SimpleBlockPlacer())
            .cannotProject().tries(32).build();

    private static final TreeFeatureConfig OAK_TREE_CONFIG = new TreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()),
            new SimpleBlockStateProvider(Blocks.OAK_LEAVES.getDefaultState()),
            new BlobFoliagePlacer(1, 0, 0, 0, 3),
            new StraightTrunkPlacer(2, 1, 0),
            new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build();

    static {
        //static block is needed because this function is not a builder
        OAK_TREE_CONFIG.ignoreFluidCheck();
    }

    public StonelandDeadForestBiome() {
        super(new Settings()
                .category(Biome.Category.PLAINS)
                .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)
                .depth(-15)
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
        DefaultBiomeFeatures.addSavannaGrass(this);

        this.addFeature(GenerationStep.Feature.RAW_GENERATION, Feature.RANDOM_PATCH
                .configure(CONFIG)
                .createDecoratedFeature(Caveman.COUNT_FLOOR_DECORATOR.configure(new CountDecoratorConfig(20))));

        this.addFeature(GenerationStep.Feature.RAW_GENERATION, Feature.TREE
                .configure(OAK_TREE_CONFIG)
                .createDecoratedFeature(Caveman.COUNT_FLOOR_DECORATOR.configure(new CountDecoratorConfig(6))));

        Biomes.PLAINS
                .getEntitySpawnList(SpawnGroup.MONSTER)
                .forEach(spawnEntry -> this.addSpawn(SpawnGroup.MONSTER, spawnEntry));
    }

    @Environment(EnvType.CLIENT)
    public int getFoliageColor() {
        return 10387789;
    }

    @Environment(EnvType.CLIENT)
    public int getGrassColorAt(double x, double z) {
        return 9470285;
    }
}
