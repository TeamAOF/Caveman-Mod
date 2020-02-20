package alloffabric.caveman.mixin;

import alloffabric.caveman.Caveman;
import alloffabric.caveman.world.CavemanChunkGeneratorConfig;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeSourceType;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.OverworldDimension;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OverworldDimension.class)
public abstract class OverworldDimensionMixin extends Dimension {
    public OverworldDimensionMixin(World world, DimensionType type) {
        super(world, type, 0.0F);
    }

    @Inject(method = "createChunkGenerator", at = @At("HEAD"), cancellable = true)
    public void createChunkGenerator(CallbackInfoReturnable<ChunkGenerator<? extends ChunkGeneratorConfig>> info) {
        if (this.world.getLevelProperties().getGeneratorType() == Caveman.LEVEL_GEN_TYPE) {
            info.setReturnValue(Caveman.CHUNK_GEN_TYPE.create(
                this.world,
                BiomeSourceType.FIXED.applyConfig(BiomeSourceType.FIXED
                    .getConfig(this.world.getLevelProperties())
                    .setBiome(Caveman.STONELAND_BIOME)
                ),
                new CavemanChunkGeneratorConfig()
            ));
        }
    }

}
