package alloffabric.caveman.structure.pool;

import alloffabric.caveman.Caveman;
import com.mojang.datafixers.Dynamic;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePoolElementType;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;

public class NormalizedPoolElement extends SinglePoolElement {
    public final boolean rotatable;

    public NormalizedPoolElement(Identifier id) {
        this(id, true);
    }

    public NormalizedPoolElement(Identifier id, boolean rotatable) {
        super(id.toString());
        this.rotatable = rotatable;
    }

    public NormalizedPoolElement(Dynamic<?> dynamic) {
        super(dynamic);
        this.rotatable = true;
    }

    @Override
    protected StructurePlacementData method_16616(BlockRotation rotation, BlockBox box) {
        return super.method_16616(rotation, box)
            .removeProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS)
            .addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
    }

    @Override
    public StructurePoolElementType getType() {
        return Caveman.NORMALIZED_POOL_ELEMENT;
    }
}
