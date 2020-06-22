package dev.aofmc.caveman.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.gen.chunk.NoiseConfig;
import net.minecraft.world.gen.chunk.StructuresConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Optional;

@Mixin(ChunkGeneratorType.class)
public interface ChunkGeneratorTypeAccessor {
    @Invoker("<init>")
    static ChunkGeneratorType init(StructuresConfig config, NoiseConfig noiseConfig, BlockState defaultBlock, BlockState defaultFluid, int bedrockCeilingY, int bedrockFloorY, int k, boolean bl, Optional<ChunkGeneratorType.Preset> optional) {
        throw new UnsupportedOperationException();
    }
}
