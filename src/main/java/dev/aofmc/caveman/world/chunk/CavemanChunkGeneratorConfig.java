package dev.aofmc.caveman.world.chunk;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class CavemanChunkGeneratorConfig {
    private final int strongholdDistance;
    private final int templeDistance;
    private final BlockState defaultFluid;

    public CavemanChunkGeneratorConfig() {
        this.strongholdDistance = 8; // Instead of 32
        this.templeDistance = 12; // Instead of 32
        this.defaultFluid = Blocks.AIR.getDefaultState();
    }

    public int getTempleSeparation() {
        return 8; // Instead of 8
    }

    public int getBedrockFloorY() {
        return 0;
    }

    public int getBedrockCeilingY() {
        return 255;
    }
}
