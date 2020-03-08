package alloffabric.caveman.mixin;

import alloffabric.caveman.structure.processor.NoIgnoreAirProcessor;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SinglePoolElement.class)
public class SinglePoolElementMixin {
    @Inject(method = "method_16616", at = @At("RETURN"), cancellable = true)
    private void createPlacementData(BlockRotation rotation, BlockBox box, CallbackInfoReturnable<StructurePlacementData> cir) {
        StructurePlacementData placementData = cir.getReturnValue();
        if (placementData.getProcessors().contains(NoIgnoreAirProcessor.INSTANCE)) {
            placementData.removeProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR);
            if (placementData.getProcessors().contains(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS)) {
                placementData.removeProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS);
                placementData.addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
            }
        }
        cir.setReturnValue(placementData);
    }
}
