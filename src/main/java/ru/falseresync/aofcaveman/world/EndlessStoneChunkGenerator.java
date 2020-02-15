package ru.falseresync.aofcaveman.worldgen;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;

public class EndlessStoneChunkGenerator extends ChunkGenerator<ChunkGeneratorConfig> {
    public EndlessStoneChunkGenerator(IWorld world, BiomeSource biomeSource, ChunkGeneratorConfig config) {
        super(world, biomeSource, config);
    }

    @Override
    public void buildSurface(ChunkRegion chunkRegion, Chunk chunk) {
    }

    @Override
    public int getSpawnHeight() {
        return 64;
    }

    @Override
    public void populateNoise(IWorld world, Chunk chunk) {
        BlockState stone = Blocks.STONE.getDefaultState();
        BlockState bedrock = Blocks.BEDROCK.getDefaultState();
        Heightmap heightmap = chunk.getHeightmap(Heightmap.Type.WORLD_SURFACE_WG);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                chunk.setBlockState(new BlockPos(x, 1, z), bedrock, false);
                heightmap.trackUpdate(x, 1, z, bedrock);
            }
        }

        for (int x = 0; x < 16; x++) {
            for (int y = 1; y < 255; y++) {
                for (int z = 0; z < 16; z++) {
                    chunk.setBlockState(new BlockPos(x, y, z), stone, false);
                    heightmap.trackUpdate(x, y, z, stone);
                }
            }
        }

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                chunk.setBlockState(new BlockPos(x, 255, z), bedrock, false);
                heightmap.trackUpdate(x, 255, z, bedrock);
            }
        }
    }

    @Override
    public int getHeightOnGround(int x, int z, Heightmap.Type heightmapType) {
        return 64;
    }

    @Override
    public void generateFeatures(ChunkRegion region) {
    }
}
