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
import net.minecraft.world.gen.feature.AbstractTempleFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.function.Function;

public class MacroDungeonFeature extends AbstractTempleFeature<DefaultFeatureConfig> {
    public MacroDungeonFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configFactory) {
        super(configFactory);
    }

    @Override
    protected int getSeedModifier() {
        return 0;
    }

    @Override
    public StructureStartFactory getStructureStartFactory() {
        return Start::new;
    }

    @Override
    public String getName() {
        return Caveman.MOD_ID + ":macro_dungeon";
    }

    @Override
    public int getRadius() {
        return 10;
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
