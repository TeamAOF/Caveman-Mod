package dev.aofmc.caveman.world;

import dev.aofmc.caveman.world.chunk.CavemanBiomeSource;
import dev.aofmc.caveman.world.chunk.CavemanChunkGenerator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.world.GeneratorType;
import net.minecraft.world.gen.chunk.ChunkGenerator;

@Environment(EnvType.CLIENT)
public class CavemanGeneratorType extends GeneratorType {
    public CavemanGeneratorType() {
        super("caveman");
        GeneratorType.VALUES.add(this);
    }

    @Override
    protected ChunkGenerator method_29076(long seed) {
        return new CavemanChunkGenerator(new CavemanBiomeSource(seed), seed);
    }
}
