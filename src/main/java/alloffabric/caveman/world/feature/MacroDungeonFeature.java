package alloffabric.caveman.world.feature;

import alloffabric.caveman.structure.MacroDungeonGenerator;
import com.mojang.datafixers.Dynamic;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.AbstractTempleFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.function.Function;

import static alloffabric.caveman.util.IdentifierUtil.idString;

public class MacroDungeonFeature extends AbstractTempleFeature<DefaultFeatureConfig> {
    public MacroDungeonFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configFactory) {
        super(configFactory);
    }

    @Override
    protected int getSeedModifier(ChunkGeneratorConfig config) {
        return 0;
    }

    @Override
    public StructureStartFactory getStructureStartFactory() {
        return Start::new;
    }

    @Override
    public String getName() {
        return idString("macro_dungeon");
    }

    @Override
    public int getRadius() {
        return 12;
    }

    public static class Start extends StructureStart {
        public Start(StructureFeature<?> structureFeature, int chunkX, int chunkZ, BlockBox blockBox, int i, long l) {
            super(structureFeature, chunkX, chunkZ, blockBox, i, l);
        }

        public void init(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, int x, int z, Biome biome) {
            MacroDungeonGenerator.addPieces(
                chunkGenerator,
                structureManager,
                new BlockPos(x * 16, chunkGenerator.getMaxY(), z * 16),
                this.children,
                this.random
            );
            this.setBoundingBoxFromChildren();
            this.method_14978(chunkGenerator.getMaxY(), this.random, 0);
        }
    }
}
