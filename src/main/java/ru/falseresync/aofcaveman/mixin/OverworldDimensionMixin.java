package ru.falseresync.aofcaveman.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.source.BiomeSourceType;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.OverworldDimension;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.FloatingIslandsChunkGeneratorConfig;
import net.minecraft.world.level.LevelGeneratorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.falseresync.aofcaveman.AOFCaveman;

@Mixin(OverworldDimension.class)
public abstract class OverworldDimensionMixin extends Dimension {
    public OverworldDimensionMixin(World world, DimensionType type) {
        super(world, type, 0.0F);
    }

    @Inject(method = "createChunkGenerator", at = @At("RETURN"), cancellable = true)
    public void createChunkGenerator(CallbackInfoReturnable<ChunkGenerator<? extends ChunkGeneratorConfig>> info) {
        LevelGeneratorType type = this.world.getLevelProperties().getGeneratorType();

        if (type == AOFCaveman.LEVEL_GEN_TYPE) {
            info.setReturnValue(AOFCaveman.CHUNK_GEN_TYPE.create(
                    this.world,
                    BiomeSourceType.FIXED.applyConfig(BiomeSourceType.FIXED
                            .getConfig(this.world.getLevelProperties())
                            .setBiome(Biomes.PLAINS)
                    ),
                    new FloatingIslandsChunkGeneratorConfig().withCenter(new BlockPos(0, 64, 0))
            ));
        }
    }

}
