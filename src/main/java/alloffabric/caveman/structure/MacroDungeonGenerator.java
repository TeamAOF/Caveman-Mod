package alloffabric.caveman.structure;

import alloffabric.caveman.Caveman;
import alloffabric.caveman.structure.pool.NormalizedPoolElement;
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
    public static void addPieces(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, BlockPos pos, List<StructurePiece> pieces, ChunkRandom random) {
        StructurePoolBasedGenerator.addPieces(
            id("macro_dungeon/lobby"),
            10,
            (StructureManager manager,
             StructurePoolElement element,
             BlockPos piecePos,
             int groundLevelDelta,
             BlockRotation rotation,
             BlockBox boundingBox
            ) -> {
                if (element instanceof NormalizedPoolElement) {
                    rotation = ((NormalizedPoolElement) element).rotatable ? rotation : BlockRotation.NONE;
                }
                return new Piece(manager, element, piecePos, groundLevelDelta, rotation, boundingBox);
            },
            chunkGenerator,
            structureManager,
            pos,
            pieces,
            random
        );
    }

    static {
        StructurePoolBasedGenerator.REGISTRY.add(new StructurePool(
            id("macro_dungeon/lobby"),
            new Identifier("empty"),
            ImmutableList.of(
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/x_intersection/lit")), 1)
            ),
            StructurePool.Projection.RIGID
        ));

        StructurePoolBasedGenerator.REGISTRY.add(new StructurePool(
            id("macro_dungeon/hallways"),
            new Identifier("empty"),
            ImmutableList.of(
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/hallway/default")), 6),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/hallway/lit")), 1),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/hallway/ominous")), 1)
            ),
            StructurePool.Projection.RIGID
        ));

        StructurePoolBasedGenerator.REGISTRY.add(new StructurePool(
            id("macro_dungeon/stairways/down"),
            new Identifier("empty"),
            ImmutableList.of(
                // Hallways
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/hallway/stairway/up/default")), 6),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/hallway/stairway/up/lit")), 1),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/hallway/stairway/up/ominous")), 1),
                // T-Intersections
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/t_intersection/stairway/up/default")), 6),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/t_intersection/stairway/up/lit")), 1),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/t_intersection/stairway/up/ominous")), 1),
                // X-Intersections
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/x_intersection/stairway/up/default")), 6),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/x_intersection/stairway/up/lit")), 1),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/x_intersection/stairway/up/ominous")), 1)
            ),
            StructurePool.Projection.RIGID
        ));

        StructurePoolBasedGenerator.REGISTRY.add(new StructurePool(
            id("macro_dungeon/stairways/up"),
            new Identifier("empty"),
            ImmutableList.of(
                // Hallways
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/hallway/stairway/down/default")), 6),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/hallway/stairway/down/lit")), 1),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/hallway/stairway/down/ominous")), 1),
                // T-Intersections
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/t_intersection/stairway/down/default")), 6),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/t_intersection/stairway/down/lit")), 1),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/t_intersection/stairway/down/ominous")), 1),
                // X-Intersections
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/x_intersection/stairway/down/default")), 6),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/x_intersection/stairway/down/lit")), 1),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/x_intersection/stairway/down/ominous")), 1)
            ),
            StructurePool.Projection.RIGID
        ));

        StructurePoolBasedGenerator.REGISTRY.add(new StructurePool(
            id("macro_dungeon/premises"),
            new Identifier("empty"),
            ImmutableList.of(
                /* HALLWAYS */
                // Hallways - Weight x8
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/hallway/default")), 48),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/hallway/lit")), 8),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/hallway/ominous")), 8),
                // Stairways - Weight x1
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/hallway/stairway/down/default")), 6),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/hallway/stairway/down/lit")), 1),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/hallway/stairway/down/ominous")), 1),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/hallway/stairway/up/default")), 6),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/hallway/stairway/up/lit")), 1),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/hallway/stairway/up/ominous")), 1),

                /* T-INTERSECTIONS */
                // Hallways - Weight x4
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/t_intersection/default")), 24),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/t_intersection/lit")), 4),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/t_intersection/ominous")), 4),
                // Stairways - Weight x1
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/t_intersection/stairway/down/default")), 6),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/t_intersection/stairway/down/lit")), 1),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/t_intersection/stairway/down/ominous")), 1),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/t_intersection/stairway/up/default")), 6),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/t_intersection/stairway/up/lit")), 1),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/t_intersection/stairway/up/ominous")), 1),

                /* X-INTERSECTIONS */
                // Hallways - Weight x4
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/x_intersection/default")), 24),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/x_intersection/lit")), 4),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/x_intersection/ominous")), 4),
                // Stairways - Weight x1
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/x_intersection/stairway/down/default")), 6),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/x_intersection/stairway/down/lit")), 1),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/x_intersection/stairway/down/ominous")), 1),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/x_intersection/stairway/up/default")), 6),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/x_intersection/stairway/up/lit")), 1),
                Pair.of(new NormalizedPoolElement(id("macro_dungeon/x_intersection/stairway/up/ominous")), 1)
            ),
            StructurePool.Projection.RIGID
        ));
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
