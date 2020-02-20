package alloffabric.caveman.world;

import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;

public class CavemanChunkGeneratorConfig extends ChunkGeneratorConfig {
    public CavemanChunkGeneratorConfig() {
        this.templeDistance = 2; // Instead of 32, must be greater than templeSeparation
        this.strongholdDistance = 16; // Instead of 32
    }

    @Override
    public int getTempleSeparation() {
        return 1; // Instead of 8
    }
}
