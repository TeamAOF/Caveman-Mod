package alloffabric.caveman.structure.pool;

import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;

public class NormalizedPoolElement extends SinglePoolElement {
    public NormalizedPoolElement(Identifier id) {
        super(id.toString());
    }

    @Override
    protected StructurePlacementData method_16616(BlockRotation rotation, BlockBox box) {
        return super.method_16616(rotation, box)
            .removeProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS)
            .addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
    }
}
