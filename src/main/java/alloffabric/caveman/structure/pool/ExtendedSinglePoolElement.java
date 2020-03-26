package alloffabric.caveman.structure.pool;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.Dynamic;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElementType;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.registry.Registry;

import static alloffabric.caveman.util.IdentifierUtil.id;

public class ExtendedSinglePoolElement extends SinglePoolElement {
    public static final StructurePoolElementType TYPE = Registry.register(
        Registry.STRUCTURE_POOL_ELEMENT,
        id("extended_single_pool_element"),
        ExtendedSinglePoolElement::new);

    public ExtendedSinglePoolElement(Identifier id, ImmutableList<StructureProcessor> processors) {
        super(id.toString(), processors, StructurePool.Projection.RIGID);
    }

    public ExtendedSinglePoolElement(Dynamic<?> dynamic) {
        super(dynamic);
    }

    @Override
    public StructurePoolElementType getType() {
        return TYPE;
    }

    @Override
    protected StructurePlacementData createPlacementData(BlockRotation rotation, BlockBox box) {
        return super.createPlacementData(rotation, box).removeProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS);
    }
}
