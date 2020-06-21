package alloffabric.caveman.world.chunk;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;

public class CavemanChunkGenerator extends SurfaceChunkGenerator<CavemanChunkGeneratorConfig> {

    public CavemanChunkGenerator(IWorld world, BiomeSource biomeSource, CavemanChunkGeneratorConfig config) {
        super(world, biomeSource, 4, 8, 256, config, true);
    }

    protected void sampleNoiseColumn(double[] buffer, int x, int z) {
        this.sampleNoiseColumn(buffer, x, z, 684.412D, 684.412D, 8.555150000000001D, 4.2206D, 3, -10);
    }

    //In our chunk generator, depth is the min density and scale is the max density
    protected double[] computeNoiseRange(int x, int z) {

        double scale = 0;
        double depth = 0;

        for (int x1 = -3; x1 <= 3; x1++) {
            for (int z1 = -3; z1 <= 3; z1++) {
                scale += this.biomeSource.getBiomeForNoiseGen(x, 63, z).getScale();
                depth += this.biomeSource.getBiomeForNoiseGen(x, 63, z).getDepth();
            }
        }

        scale /= 49;
        depth /= 49;

        return new double[]{depth, scale};
    }

    protected double computeNoiseFalloff(double depth, double scale, int y) {
        //this lerps the density so most air pockets spawn near y128.
        return MathHelper.lerp(Math.abs(y - (this.getNoiseSizeY() / 2f)) / (this.getNoiseSizeY() / 2f), depth, scale);
    }

    @Override
    public int getSpawnHeight() {
        return 64;
    }
}
