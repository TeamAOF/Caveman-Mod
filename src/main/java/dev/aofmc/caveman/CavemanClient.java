package dev.aofmc.caveman;

import dev.aofmc.caveman.world.chunk.CavemanChunkGenerator;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.world.GeneratorType;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class CavemanClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        GeneratorType.VALUES.add(
            new GeneratorType("caveman") {
                protected ChunkGenerator method_29076(long l) {
                    return new CavemanChunkGenerator(
                        // Need to change signature, has to use ChunkGenType apparently
                    );
                }
            }
        );
    }
}
