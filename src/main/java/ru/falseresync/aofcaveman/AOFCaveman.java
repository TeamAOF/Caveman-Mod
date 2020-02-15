package ru.falseresync.aofcaveman;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.level.LevelGeneratorType;
import ru.falseresync.aofcaveman.mixin.LevelGeneratorTypeAccessor;
import ru.falseresync.aofcaveman.world.EndlessStoneChunkGenerator;
import ru.falseresync.aofcaveman.world.FabricChunkGeneratorType;

public class AOFCaveman implements ModInitializer {
    public static final String MODID;
    public static final String GENERATOR_NAME;
    public static final Identifier SPAWN_ROOM_ID;
    public static LevelGeneratorType LEVEL_GEN_TYPE;
    public static ChunkGeneratorType<ChunkGeneratorConfig, EndlessStoneChunkGenerator> CHUNK_GEN_TYPE;

    @Override
    public void onInitialize() {
        LEVEL_GEN_TYPE = this.getLevelGenType();
        CHUNK_GEN_TYPE = FabricChunkGeneratorType.register(
                new Identifier(MODID, GENERATOR_NAME),
                EndlessStoneChunkGenerator::new, ChunkGeneratorConfig::new, false);
    }

    private LevelGeneratorType getLevelGenType() {
        LevelGeneratorType generatorType = LevelGeneratorTypeAccessor.create(9, GENERATOR_NAME);

        LevelGeneratorType[] temp = new LevelGeneratorType[16];
        temp[0] = generatorType;
        System.arraycopy(LevelGeneratorType.TYPES, 0, temp, 1, LevelGeneratorType.TYPES.length - 1);
        System.arraycopy(temp, 0, LevelGeneratorType.TYPES, 0, temp.length);

        return generatorType;
    }

    static {
        MODID = "aof_caveman";
        GENERATOR_NAME = "endless_stone";
        SPAWN_ROOM_ID = new Identifier(MODID, "spawn_room");
    }
}
