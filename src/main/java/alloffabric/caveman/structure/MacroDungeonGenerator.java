package alloffabric.caveman.structure;

import alloffabric.caveman.Caveman;
import alloffabric.caveman.structure.processor.NoIgnoreAirProcessor;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.pool.SinglePoolElement;
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

import static alloffabric.caveman.Caveman.MOD_ID;

public class MacroDungeonGenerator {
    public static void addPieces(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, BlockPos pos, List<StructurePiece> pieces, ChunkRandom random) {
        StructurePoolBasedGenerator.addPieces(
            new Identifier(MOD_ID, "macro_dungeon/lobby"),
            10,
            Piece::new,
            chunkGenerator,
            structureManager,
            pos,
            pieces,
            random
        );
    }

    static {
        StructurePoolBasedGenerator.REGISTRY.add(new StructurePool(
            new Identifier(MOD_ID, "macro_dungeon/lobby"),
            new Identifier("empty"),
            ImmutableList.of(
                Pair.of(new SinglePoolElement(
                    MOD_ID + ":macro_dungeon/x_intersection/lit",
                    ImmutableList.of(NoIgnoreAirProcessor.INSTANCE)
                ), 1)
            ),
            StructurePool.Projection.RIGID
        ));

        StructurePoolBasedGenerator.REGISTRY.add(new StructurePool(
            new Identifier(MOD_ID, "macro_dungeon/hallways"),
            new Identifier("empty"),
            ImmutableList.of(
                Pair.of(new SinglePoolElement(
                    MOD_ID + ":macro_dungeon/hallway/default",
                    ImmutableList.of(NoIgnoreAirProcessor.INSTANCE)
                ), 5),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/hallway/lit"), 2),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/hallway/ominous"), 1)
            ),
            StructurePool.Projection.RIGID
        ));

        StructurePoolBasedGenerator.REGISTRY.add(new StructurePool(
            new Identifier(MOD_ID, "macro_dungeon/stairways/up"),
            new Identifier("empty"),
            ImmutableList.of(
                // Hallways
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/hallway/stairway/up/default"), 5),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/hallway/stairway/up/lit"), 2),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/hallway/stairway/up/ominous"), 1),
                // T-Intersections
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/t_intersection/stairway/up/default"), 5),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/t_intersection/stairway/up/lit"), 2),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/t_intersection/stairway/up/ominous"), 1),
                // X-Intersections
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/x_intersection/stairway/up/default"), 5),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/x_intersection/stairway/up/lit"), 2),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/x_intersection/stairway/up/ominous"), 1)
            ),
            StructurePool.Projection.RIGID
        ));

        StructurePoolBasedGenerator.REGISTRY.add(new StructurePool(
            new Identifier(MOD_ID, "macro_dungeon/stairways/down"),
            new Identifier("empty"),
            ImmutableList.of(
                // Hallways
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/hallway/stairway/down/default"), 5),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/hallway/stairway/down/lit"), 2),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/hallway/stairway/down/ominous"), 1),
                // T-Intersections
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/t_intersection/stairway/down/default"), 5),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/t_intersection/stairway/down/lit"), 2),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/t_intersection/stairway/down/ominous"), 1),
                // X-Intersections
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/x_intersection/stairway/down/default"), 5),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/x_intersection/stairway/down/lit"), 2),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/x_intersection/stairway/down/ominous"), 1)
            ),
            StructurePool.Projection.RIGID
        ));

        StructurePoolBasedGenerator.REGISTRY.add(new StructurePool(
            new Identifier(MOD_ID, "macro_dungeon/premises"),
            new Identifier("empty"),
            ImmutableList.of(
                /* HALLWAYS */
                // Hallways - Weight x8
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/hallway/default"), 40),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/hallway/lit"), 16),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/hallway/ominous"), 8),
                // Stairways - Weight x1
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/hallway/stairway/down/default"), 5),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/hallway/stairway/down/lit"), 2),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/hallway/stairway/down/ominous"), 1),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/hallway/stairway/up/default"), 5),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/hallway/stairway/up/lit"), 2),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/hallway/stairway/up/ominous"), 1),

                /* T-INTERSECTIONS */
                // Hallways - Weight x4
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/t_intersection/default"), 20),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/t_intersection/lit"), 8),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/t_intersection/ominous"), 4),
                // Stairways - Weight x1
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/t_intersection/stairway/down/default"), 5),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/t_intersection/stairway/down/lit"), 2),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/t_intersection/stairway/down/ominous"), 1),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/t_intersection/stairway/up/default"), 5),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/t_intersection/stairway/up/lit"), 2),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/t_intersection/stairway/up/ominous"), 1),

                /* X-INTERSECTIONS */
                // Hallways - Weight x4
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/x_intersection/default"), 20),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/x_intersection/lit"), 8),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/x_intersection/ominous"), 4),
                // Stairways - Weight x1
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/x_intersection/stairway/down/default"), 5),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/x_intersection/stairway/down/lit"), 2),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/x_intersection/stairway/down/ominous"), 1),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/x_intersection/stairway/up/default"), 5),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/x_intersection/stairway/up/lit"), 2),
                Pair.of(new SinglePoolElement(MOD_ID + ":macro_dungeon/x_intersection/stairway/up/ominous"), 1)
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
