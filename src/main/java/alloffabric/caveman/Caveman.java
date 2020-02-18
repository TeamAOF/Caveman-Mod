package alloffabric.caveman;

import alloffabric.caveman.api.IntComponent;
import alloffabric.caveman.component.RoomCounterComponent;
import alloffabric.caveman.mixin.LevelGeneratorTypeAccessor;
import alloffabric.caveman.world.EndlessStoneChunkGenerator;
import alloffabric.caveman.world.FabricChunkGeneratorType;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.Toml4jConfigSerializer;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.event.WorldComponentCallback;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.level.LevelGeneratorType;

public class Caveman implements ModInitializer {
    public static final String MODID = "caveman";
    public static final String GENERATOR_NAME;
    public static final Identifier GENERATOR_ID;
    public static final Identifier SPAWN_ROOM_ID;
    public static LevelGeneratorType LEVEL_GEN_TYPE;
    public static ChunkGeneratorType<ChunkGeneratorConfig, EndlessStoneChunkGenerator> CHUNK_GEN_TYPE;
    public static ComponentType<IntComponent> ROOM_COUNTER;
    public static CavemanConfig config;

    @Override
    public void onInitialize() {
        AutoConfig.register(CavemanConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(CavemanConfig.class).getConfig();

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
        GENERATOR_NAME = "endless_stone";
        GENERATOR_ID = new Identifier(MODID, GENERATOR_NAME);
        SPAWN_ROOM_ID = new Identifier(MODID, "spawn_room");
    }
}
