package ru.falseresync.aofcaveman;

import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.event.WorldComponentCallback;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.level.LevelGeneratorType;
import ru.falseresync.aofcaveman.api.IntComponent;
import ru.falseresync.aofcaveman.component.RoomCounterComponent;
import ru.falseresync.aofcaveman.mixin.LevelGeneratorTypeAccessor;
import ru.falseresync.aofcaveman.world.EndlessStoneChunkGenerator;
import ru.falseresync.aofcaveman.world.FabricChunkGeneratorType;

public class AOFCaveman implements ModInitializer {
    public static final String MODID;
    public static final String GENERATOR_NAME;
    public static final Identifier GENERATOR_ID;
    public static final Identifier SPAWN_ROOM_ID;
    public static LevelGeneratorType LEVEL_GEN_TYPE;
    public static ChunkGeneratorType<ChunkGeneratorConfig, EndlessStoneChunkGenerator> CHUNK_GEN_TYPE;
    public static ComponentType<IntComponent> ROOM_COUNTER;

    @Override
    public void onInitialize() {
        LEVEL_GEN_TYPE = LevelGeneratorTypeAccessor.create(9, GENERATOR_NAME);
        CHUNK_GEN_TYPE = FabricChunkGeneratorType.register(
                GENERATOR_ID,
                EndlessStoneChunkGenerator::new,
                ChunkGeneratorConfig::new,
                false);
        ROOM_COUNTER = ComponentRegistry.INSTANCE
                .registerIfAbsent(RoomCounterComponent.ID, IntComponent.class)
                .attach(WorldComponentCallback.EVENT, RoomCounterComponent::new);
    }

    static {
        MODID = "aof_caveman";
        GENERATOR_NAME = "endless_stone";
        GENERATOR_ID = new Identifier(MODID, GENERATOR_NAME);
        SPAWN_ROOM_ID = new Identifier(MODID, "spawn_room");
    }
}
