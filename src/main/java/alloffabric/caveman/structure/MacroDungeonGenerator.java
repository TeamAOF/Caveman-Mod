package alloffabric.caveman.structure;

import alloffabric.caveman.Caveman;
import alloffabric.caveman.structure.pool.ExtendedSinglePoolElement;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.List;

import static alloffabric.caveman.util.IdentifierUtil.id;

public class MacroDungeonGenerator {
    static {
        pool(
            id("macro_dungeon/lobby"),
            ImmutableList.of(
                element(id("macro_dungeon/x_intersection/lit"), 1)
            )
        );

        pool(
            id("macro_dungeon/hallways"),
            ImmutableList.of(
                element(id("macro_dungeon/hallway/default"), 6),
                element(id("macro_dungeon/hallway/lit"), 1),
                element(id("macro_dungeon/hallway/ominous"), 1)
            )
        );

        pool(
            id("macro_dungeon/stairways/down"),
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

    public static void addPieces(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, BlockPos pos, List<StructurePiece> pieces, ChunkRandom random) {
        StructurePoolBasedGenerator.addPieces(
            id("macro_dungeon/lobby"),
            10,
            Piece::new,
            chunkGenerator,
            structureManager,
            pos,
            pieces,
            random,
            true, // Unknown shit
            true // Unknown shit 2
        );
    }

    private static Pair<StructurePoolElement, Integer> element(Identifier id, Integer weight) {
        return Pair.of(new ExtendedSinglePoolElement(id, ImmutableList.of()), weight);
    }

    private static void pool(Identifier id, List<Pair<StructurePoolElement, Integer>> elements) {
        StructurePoolBasedGenerator.REGISTRY.add(new StructurePool(id, new Identifier("empty"), elements, StructurePool.Projection.RIGID));
    }

    private static void pool(Identifier id, Identifier terminatorId, List<Pair<StructurePoolElement, Integer>> elements) {
        StructurePoolBasedGenerator.REGISTRY.add(new StructurePool(id, terminatorId, elements, StructurePool.Projection.RIGID));
    }

    public static class Piece extends PoolStructurePiece {
        public Piece(StructureManager manager, StructurePoolElement element, BlockPos pos, int groundLevelDelta, BlockRotation rotation, BlockBox boundingBox) {
            super(Caveman.MACRO_DUNGEON_PIECE, manager, element, pos, groundLevelDelta, rotation, boundingBox);
        }

        public Piece(StructureManager manager, CompoundTag tag) {
            super(manager, tag, Caveman.MACRO_DUNGEON_PIECE);
        }
    }
}
