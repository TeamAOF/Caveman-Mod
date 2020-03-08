package alloffabric.caveman.structure.processor;

import alloffabric.caveman.Caveman;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class NoIgnoreAirProcessor extends StructureProcessor {
    public static final NoIgnoreAirProcessor INSTANCE = new NoIgnoreAirProcessor();

    private NoIgnoreAirProcessor() {
    }

    @Override
    public Structure.StructureBlockInfo process(
        WorldView view,
        BlockPos pos,
        Structure.StructureBlockInfo blockInfo1,
        Structure.StructureBlockInfo blockInfo2,
        StructurePlacementData placementData
    ) {
        return blockInfo2;
    }

    @Override
    protected StructureProcessorType getType() {
        return Caveman.NO_IGNORE_AIR_STRUCTURE_PROCESSOR;
    }

    @Override
    protected <T> Dynamic<T> method_16666(DynamicOps<T> dynamicOps) {
        return new Dynamic<>(dynamicOps, dynamicOps.empty());
    }
}
