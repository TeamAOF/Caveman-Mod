package alloffabric.caveman.structure;

import alloffabric.caveman.Caveman;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.*;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MiniDungeonGenerator {
    private static final List<Identifier> DUNGEONS = Arrays.stream(Caveman.CONFIG.miniDungeons.structures)
        .map(Identifier::new).collect(Collectors.toList());

    public static void addPieces(StructureManager manager, List<StructurePiece> pieces, Random random, BlockPos pos) {
        BlockRotation blockRotation = BlockRotation.random(random);
        pieces.add(new Piece(manager, DUNGEONS.get(random.nextInt(DUNGEONS.size())), pos, blockRotation));
    }

    public static class Piece extends SimpleStructurePiece {
        private final Identifier template;
        private final BlockRotation structureRotation;

        public Piece(StructureManager manager, CompoundTag tag) {
            super(Caveman.MINI_DUNGEON_PIECE, tag);
            this.template = new Identifier(tag.getString("Template"));
            this.structureRotation = BlockRotation.valueOf(tag.getString("Rotation"));
            this.initializeStructureData(manager);
        }

        public Piece(StructureManager manager, Identifier template, BlockPos pos, BlockRotation rotation) {
            super(Caveman.MINI_DUNGEON_PIECE, 0);
            this.template = template;
            this.pos = pos;
            this.structureRotation = rotation;
            this.initializeStructureData(manager);
        }

        private void initializeStructureData(StructureManager manager) {
            Structure structure = manager.getStructureOrBlank(this.template);
            StructurePlacementData structurePlacementData = new StructurePlacementData()
                .setRotation(this.structureRotation)
                .setMirror(BlockMirror.NONE);
            this.setStructureData(structure, this.pos, structurePlacementData);
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putString("Template", this.template.toString());
            tag.putString("Rotation", this.structureRotation.name());
        }

        @Override
        protected void handleMetadata(String metadata, BlockPos pos, IWorld world, Random random, BlockBox boundingBox) {
        }
    }
}
