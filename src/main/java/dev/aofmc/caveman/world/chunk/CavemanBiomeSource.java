package dev.aofmc.caveman.world.chunk;

import dev.aofmc.caveman.Caveman;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.biome.source.BiomeSource;

public class CavemanBiomeSource extends BiomeSource {
    public static final Codec<CavemanBiomeSource> CODEC = RecordCodecBuilder
        .create(instance -> instance
            .group(
                Codec.LONG.fieldOf("seed")
                    .stable()
                    .forGetter(source -> source.seed)
            )
            .apply(instance, instance.stable(CavemanBiomeSource::new)));
    private final long seed;
    private final BiomeLayerSampler biomeSampler;

    public CavemanBiomeSource(long seed) {
        super(ImmutableList.of(Caveman.STONELAND_BIOME, Caveman.STONELAND_FOREST_BIOME));
        this.seed = seed;
        this.biomeSampler = CavemanBiomeLayers.build(seed);
    }

    @Override
    public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
        return biomeSampler.sample(biomeX, biomeZ);
    }

    @Override
    protected Codec<? extends BiomeSource> method_28442() {
        return CODEC;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public BiomeSource withSeed(long seed) {
        return new CavemanBiomeSource(seed);
    }
}
