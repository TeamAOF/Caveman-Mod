package dev.aofmc.caveman.world.chunk.layer;

import dev.aofmc.caveman.Caveman;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.layer.type.InitLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public enum CavemanInitLayer implements InitLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int x, int y) {
        return context.nextInt(10) == 0 ? Registry.BIOME.getRawId(Caveman.STONELAND_FOREST_BIOME) : Registry.BIOME.getRawId(Caveman.STONELAND_BIOME);
    }
}
