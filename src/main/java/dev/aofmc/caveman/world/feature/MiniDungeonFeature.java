package dev.aofmc.caveman.world.feature;

import dev.aofmc.caveman.Caveman;
import dev.aofmc.caveman.structure.MiniDungeonGenerator;
import com.mojang.serialization.Codec;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class MiniDungeonFeature extends StructureFeature<DefaultFeatureConfig> {
    public MiniDungeonFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long l, ChunkRandom random, int i, int j, Biome biome, ChunkPos chunkPos, DefaultFeatureConfig featureConfig) {
        return random.nextDouble() < Caveman.CONFIG.miniDungeons.commonness;
    }

    @Override
    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return Start::new;
    }

    @Override
    public GenerationStep.Feature method_28663() {
        return GenerationStep.Feature.UNDERGROUND_STRUCTURES;
    }

    @Override
    public String getName() {
        return Caveman.MOD_ID + ":mini_dungeon";
    }

    public static class Start extends StructureStart<DefaultFeatureConfig> {
        public Start(StructureFeature<DefaultFeatureConfig> structureFeature, int chunkX, int chunkZ, BlockBox blockBox, int i, long l) {
            super(structureFeature, chunkX, chunkZ, blockBox, i, l);
        }

        @Override
        public void init(ChunkGenerator chunkGenerator, StructureManager structureManager, int x, int z, Biome biome, DefaultFeatureConfig featureConfig) {
            int i = x * 16;
            int j = z * 16;
            MiniDungeonGenerator.addPieces(
                structureManager,
                this.children,
                this.random,
                new BlockPos(
                    i + this.random.nextInt(16),
                    10 + this.random.nextInt(230),
                    j + this.random.nextInt(16)
                )
            );
            this.setBoundingBoxFromChildren();
        }
    }
}
