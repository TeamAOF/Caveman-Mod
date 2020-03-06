package alloffabric.caveman.structure;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.*;
import net.minecraft.structure.pool.*;
import net.minecraft.structure.processor.BlockRotStructureProcessor;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static alloffabric.caveman.Caveman.MOD_ID;

public class MacroDungeonGenerator {
    private static final Set<Identifier> DUNGEONS = Sets.newHashSet(
        "caveman:macro_dungeon_0_hallway_0",
        "caveman:macro_dungeon_0_hallway_0_lit",
        "caveman:macro_dungeon_0_hallway_0_ominous",
        "caveman:macro_dungeon_0_stairway_down_0",
        "caveman:macro_dungeon_0_stairway_down_0_lit",
        "caveman:macro_dungeon_0_stairway_down_0_ominous",
        "caveman:macro_dungeon_0_stairway_up_0",
        "caveman:macro_dungeon_0_stairway_up_0_lit",
        "caveman:macro_dungeon_0_stairway_up_0_ominous",
        "caveman:macro_dungeon_0_t_intersection_0",
        "caveman:macro_dungeon_0_t_intersection_0_lit",
        "caveman:macro_dungeon_0_t_intersection_0_ominous",
        "caveman:macro_dungeon_0_t_intersection_0_stairway_down",
        "caveman:macro_dungeon_0_t_intersection_0_stairway_down_lit",
        "caveman:macro_dungeon_0_t_intersection_0_stairway_down_ominous",
        "caveman:macro_dungeon_0_t_intersection_0_stairway_up",
        "caveman:macro_dungeon_0_t_intersection_0_stairway_up_ominous",
        "caveman:macro_dungeon_0_x_intersection_0",
        "caveman:macro_dungeon_0_x_intersection_0_lit",
        "caveman:macro_dungeon_0_x_intersection_0_ominous",
        "caveman:macro_dungeon_0_x_intersection_0_stairway_down",
        "caveman:macro_dungeon_0_x_intersection_0_stairway_down_lit",
        "caveman:macro_dungeon_0_x_intersection_0_stairway_down_ominous",
        "caveman:macro_dungeon_0_x_intersection_0_stairway_up",
        "caveman:macro_dungeon_0_x_intersection_0_stairway_up_lit",
        "caveman:macro_dungeon_0_x_intersection_0_stairway_up_ominous"
    ).stream().map(Identifier::new).collect(Collectors.toSet());

    static {
        StructurePoolBasedGenerator.REGISTRY.add(new StructurePool(
            new Identifier(MOD_ID, "pillager_outpost/base_plates"),
            new Identifier(MOD_ID, "empty"),
            ImmutableList.of(Pair.of(new SinglePoolElement("pillager_outpost/base_plate"), 1)),
            StructurePool.Projection.RIGID
        ));
        StructurePoolBasedGenerator.REGISTRY.add(new StructurePool(
            new Identifier("pillager_outpost/towers"),
            new Identifier("empty"),
            ImmutableList.of(Pair.of(
                new ListPoolElement(ImmutableList.of(
                    new SinglePoolElement("pillager_outpost/watchtower"),
                    new SinglePoolElement(
                        "pillager_outpost/watchtower_overgrown",
                        ImmutableList.of(new BlockRotStructureProcessor(0.05F)),
                        StructurePool.Projection.RIGID
                    )
                )),
                1
            )),
            StructurePool.Projection.RIGID
        ));
        StructurePoolBasedGenerator.REGISTRY.add(new StructurePool(
            new Identifier("pillager_outpost/feature_plates"),
            new Identifier("empty"),
            ImmutableList.of(Pair.of(new SinglePoolElement("pillager_outpost/feature_plate"), 1)),
            StructurePool.Projection.TERRAIN_MATCHING
        ));
        StructurePoolBasedGenerator.REGISTRY.add(new StructurePool(
            new Identifier("pillager_outpost/features"),
            new Identifier("empty"),
            ImmutableList.of(
                Pair.of(new SinglePoolElement("pillager_outpost/feature_cage1"), 1),
                Pair.of(new SinglePoolElement("pillager_outpost/feature_cage2"), 1),
                Pair.of(new SinglePoolElement("pillager_outpost/feature_logs"), 1),
                Pair.of(new SinglePoolElement("pillager_outpost/feature_tent1"), 1),
                Pair.of(new SinglePoolElement("pillager_outpost/feature_tent2"), 1),
                Pair.of(new SinglePoolElement("pillager_outpost/feature_targets"), 1),
                Pair.of(EmptyPoolElement.INSTANCE, 6)),
            StructurePool.Projection.RIGID
        ));
    }

    public static void addPieces(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, BlockPos pos, List<StructurePiece> pieces, ChunkRandom random) {
        StructurePoolBasedGenerator.addPieces(
            new Identifier(MOD_ID, "macro_dungeons/base_plates"),
            7,
            PillagerOutpostGenerator.Piece::new,
            chunkGenerator,
            structureManager,
            pos,
            pieces,
            random
        );
    }

    public static class Piece extends PoolStructurePiece {
        public Piece(StructureManager manager, StructurePoolElement element, BlockPos pos, int groundLevelDelta, BlockRotation rotation, BlockBox boundingBox) {
            super(StructurePieceType.PILLAGER_OUTPOST, manager, element, pos, groundLevelDelta, rotation, boundingBox);
        }

        public Piece(StructureManager manager, CompoundTag tag) {
            super(manager, tag, StructurePieceType.PILLAGER_OUTPOST);
        }
    }
}
