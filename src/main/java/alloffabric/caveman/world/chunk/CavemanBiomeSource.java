package alloffabric.caveman.world.chunk;

import alloffabric.caveman.Caveman;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.VanillaLayeredBiomeSourceConfig;

public class CavemanBiomeSource extends BiomeSource {
    private final BiomeLayerSampler biomeSampler;

    public CavemanBiomeSource(VanillaLayeredBiomeSourceConfig config) {
        super(ImmutableSet.of(Caveman.STONELAND_BIOME, Caveman.STONELAND_FOREST_BIOME));
        this.biomeSampler = CavemanBiomeLayers.build(config.getSeed(), config.getGeneratorType(), config.getGeneratorConfig());
    }

    @Override
    public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
        return biomeSampler.sample(biomeX, biomeZ);
    }
}
