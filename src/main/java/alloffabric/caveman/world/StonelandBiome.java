package alloffabric.caveman.world;

import net.minecraft.entity.EntityCategory;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class StonelandBiome extends Biome {
    protected StonelandBiome(Settings settings) {
        super(settings);

        this.addStructureFeature(Feature.STRONGHOLD.configure(FeatureConfig.DEFAULT));

        DefaultBiomeFeatures.addClay(this);
        DefaultBiomeFeatures.addDungeons(this);
        DefaultBiomeFeatures.addFossils(this);
        DefaultBiomeFeatures.addMineables(this);

        Biomes.PLAINS
            .getEntitySpawnList(EntityCategory.MONSTER)
            .forEach(spawnEntry -> this.addSpawn(EntityCategory.MONSTER, spawnEntry));
    }

}
