package alloffabric.caveman.world.feature;

import alloffabric.caveman.Caveman;
import alloffabric.caveman.structure.MacroDungeonGenerator;
import com.mojang.datafixers.Dynamic;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.VillageStructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Random;
import java.util.function.Function;

public class MacroDungeonFeature extends StructureFeature<DefaultFeatureConfig> {
    public MacroDungeonFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configFactory) {
        super(configFactory);
    }

    @Override
    public boolean shouldStartAt(BiomeAccess biomeAccess, ChunkGenerator<?> generator, Random random, int chunkX, int chunkZ, Biome biome) {
        return random.nextDouble() < 0.1; //Caveman.CONFIG.miniDungeons.commonness;
    }

    @Override
    public StructureStartFactory getStructureStartFactory() {
        return MiniDungeonFeature.Start::new;
    }

    @Override
    public String getName() {
        return Caveman.MOD_ID + ":macro_dungeon";
    }

    @Override
    public int getRadius() {
        return 5;
    }

    public static class Start extends VillageStructureStart {
        public Start(StructureFeature<?> structureFeature, int chunkX, int chunkZ, BlockBox blockBox, int i, long l) {
            super(structureFeature, chunkX, chunkZ, blockBox, i, l);
        }

        public void initialize(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, int x, int z, Biome biome) {
            int i = x * 16;
            int j = z * 16;
            MacroDungeonGenerator.addPieces(
                chunkGenerator,
                structureManager,
                new BlockPos(
                    i + this.random.nextInt(16),
                    10 + this.random.nextInt(230),
                    j + this.random.nextInt(16)
                ),
                this.children,
                this.random
            );
            this.setBoundingBoxFromChildren();
        }
    }
}
