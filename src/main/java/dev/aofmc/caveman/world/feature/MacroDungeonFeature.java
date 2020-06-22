package dev.aofmc.caveman.world.feature;

import dev.aofmc.caveman.Caveman;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import robosky.structurehelpers.structure.ExtendedStructures;
import robosky.structurehelpers.structure.pool.ExtendedSinglePoolElement;

import java.util.List;
import static dev.aofmc.caveman.util.IdentifierUtil.id;
import static dev.aofmc.caveman.util.IdentifierUtil.idString;

public class MacroDungeonFeature extends StructureFeature<DefaultFeatureConfig> {
    static {
        pool(
            id("macro_dungeon/lobby"),
            ImmutableList.of(
                element(id("macro_dungeon/x_intersection/lit"), 1)
            )
        );

        pool(
            id("macro_dungeon/hallways"),
            id("macro_dungeon/hallway/terminator"),
            ImmutableList.of(
                element(id("macro_dungeon/hallway/default"), 6),
                element(id("macro_dungeon/hallway/lit"), 1),
                element(id("macro_dungeon/hallway/ominous"), 1)
            )
        );

        pool(
            id("macro_dungeon/stairways/down"),
            id("macro_dungeon/hallway/stairway/down/terminator"),
            ImmutableList.of(
                // Hallways
                element(id("macro_dungeon/hallway/stairway/up/default"), 6),
                element(id("macro_dungeon/hallway/stairway/up/lit"), 1),
                element(id("macro_dungeon/hallway/stairway/up/ominous"), 1),
                // T-Intersections
                element(id("macro_dungeon/t_intersection/stairway/up/default"), 6),
                element(id("macro_dungeon/t_intersection/stairway/up/lit"), 1),
                element(id("macro_dungeon/t_intersection/stairway/up/ominous"), 1),
                // X-Intersections
                element(id("macro_dungeon/x_intersection/stairway/up/default"), 6),
                element(id("macro_dungeon/x_intersection/stairway/up/lit"), 1),
                element(id("macro_dungeon/x_intersection/stairway/up/ominous"), 1)
            )
        );

        pool(
            id("macro_dungeon/stairways/up"),
            id("macro_dungeon/hallway/stairway/up/terminator"),
            ImmutableList.of(
                // Hallways
                element(id("macro_dungeon/hallway/stairway/down/default"), 6),
                element(id("macro_dungeon/hallway/stairway/down/lit"), 1),
                element(id("macro_dungeon/hallway/stairway/down/ominous"), 1),
                // T-Intersections
                element(id("macro_dungeon/t_intersection/stairway/down/default"), 6),
                element(id("macro_dungeon/t_intersection/stairway/down/lit"), 1),
                element(id("macro_dungeon/t_intersection/stairway/down/ominous"), 1),
                // X-Intersections
                element(id("macro_dungeon/x_intersection/stairway/down/default"), 6),
                element(id("macro_dungeon/x_intersection/stairway/down/lit"), 1),
                element(id("macro_dungeon/x_intersection/stairway/down/ominous"), 1)
            )
        );

        pool(
            id("macro_dungeon/premises"),
            id("macro_dungeon/hallway/terminator"),
            ImmutableList.of(
                /* HALLWAYS */
                // Hallways - Weight x8
                element(id("macro_dungeon/hallway/default"), 48),
                element(id("macro_dungeon/hallway/lit"), 8),
                element(id("macro_dungeon/hallway/ominous"), 8),
                // Stairways - Weight x1
                element(id("macro_dungeon/hallway/stairway/down/default"), 6),
                element(id("macro_dungeon/hallway/stairway/down/lit"), 1),
                element(id("macro_dungeon/hallway/stairway/down/ominous"), 1),
                element(id("macro_dungeon/hallway/stairway/up/default"), 6),
                element(id("macro_dungeon/hallway/stairway/up/lit"), 1),
                element(id("macro_dungeon/hallway/stairway/up/ominous"), 1),

                /* T-INTERSECTIONS */
                // Hallways - Weight x4
                element(id("macro_dungeon/t_intersection/default"), 24),
                element(id("macro_dungeon/t_intersection/lit"), 4),
                element(id("macro_dungeon/t_intersection/ominous"), 4),
                // Stairways - Weight x1
                element(id("macro_dungeon/t_intersection/stairway/down/default"), 6),
                element(id("macro_dungeon/t_intersection/stairway/down/lit"), 1),
                element(id("macro_dungeon/t_intersection/stairway/down/ominous"), 1),
                element(id("macro_dungeon/t_intersection/stairway/up/default"), 6),
                element(id("macro_dungeon/t_intersection/stairway/up/lit"), 1),
                element(id("macro_dungeon/t_intersection/stairway/up/ominous"), 1),

                /* X-INTERSECTIONS */
                // Hallways - Weight x4
                element(id("macro_dungeon/x_intersection/default"), 24),
                element(id("macro_dungeon/x_intersection/lit"), 4),
                element(id("macro_dungeon/x_intersection/ominous"), 4),
                // Stairways - Weight x1
                element(id("macro_dungeon/x_intersection/stairway/down/default"), 6),
                element(id("macro_dungeon/x_intersection/stairway/down/lit"), 1),
                element(id("macro_dungeon/x_intersection/stairway/down/ominous"), 1),
                element(id("macro_dungeon/x_intersection/stairway/up/default"), 6),
                element(id("macro_dungeon/x_intersection/stairway/up/lit"), 1),
                element(id("macro_dungeon/x_intersection/stairway/up/ominous"), 1)
            )
        );
    }

    public MacroDungeonFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    private static Pair<StructurePoolElement, Integer> element(Identifier id, Integer weight) {
        return Pair.of(new ExtendedSinglePoolElement(id, false, ImmutableList.of()), weight);
    }

    private static void pool(Identifier id, List<Pair<StructurePoolElement, Integer>> elements) {
        StructurePoolBasedGenerator.REGISTRY.add(new StructurePool(id, new Identifier("empty"), elements, StructurePool.Projection.RIGID));
    }

    private static void pool(Identifier id, Identifier terminatorId, List<Pair<StructurePoolElement, Integer>> elements) {
        StructurePoolBasedGenerator.REGISTRY.add(new StructurePool(id, terminatorId, elements, StructurePool.Projection.RIGID));
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
        return idString("macro_dungeon");
    }


    public static class Start extends StructureStart<DefaultFeatureConfig> {
        public Start(StructureFeature<DefaultFeatureConfig> feature, int chunkX, int chunkZ, BlockBox box, int references, long seed) {
            super(feature, chunkX, chunkZ, box, references, seed);
        }

        @Override
        public void init(ChunkGenerator chunkGenerator, StructureManager structureManager, int x, int z, Biome biome, DefaultFeatureConfig featureConfig) {
            List<PoolStructurePiece> pieces = ExtendedStructures.addPieces(
                ImmutableList.of(),
                80,
                80,
                id("macro_dungeon/lobby"),
                10,
                Caveman.MACRO_DUNGEON_PIECE,
                chunkGenerator,
                structureManager,
                new BlockPos(x * 16, chunkGenerator.getMaxY(), z * 16),
                this.random,
                true, // Unknown shit
                false
            );
            this.children.addAll(pieces);
            this.setBoundingBoxFromChildren();
            this.method_14978(chunkGenerator.getMaxY(), this.random, 0);
        }
    }
}
