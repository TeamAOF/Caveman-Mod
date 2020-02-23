package alloffabric.caveman.world.chunk;

import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;

public class CavemanChunkGeneratorConfig extends ChunkGeneratorConfig {
    public CavemanChunkGeneratorConfig() {
        this.strongholdDistance = 12; // Instead of 32
    }
}
