package alloffabric.caveman;

import alloffabric.caveman.api.IntComponent;
import alloffabric.caveman.command.TimedSpawnerCommand;
import alloffabric.caveman.component.RoomCounterComponent;
import alloffabric.caveman.mixin.LevelGeneratorTypeAccessor;
import alloffabric.caveman.structure.MiniDungeonGenerator;
import alloffabric.caveman.world.CavemanChunkGenerator;
import alloffabric.caveman.world.FabricChunkGeneratorType;
import alloffabric.caveman.world.MiniDungeonFeature;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.Toml4jConfigSerializer;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.event.WorldComponentCallback;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.level.LevelGeneratorType;

public class Caveman implements ModInitializer {
    public static final String MODID = "caveman";
    public static final String GENERATOR_NAME;
    public static final Identifier GENERATOR_ID;
    public static LevelGeneratorType LEVEL_GEN_TYPE;
    public static ChunkGeneratorType<ChunkGeneratorConfig, CavemanChunkGenerator> CHUNK_GEN_TYPE;
    public static ComponentType<IntComponent> ROOM_COUNTER;
    public static CavemanConfig config;
    public static StructurePieceType MINI_DUNGEON_PIECE;
    public static StructureFeature<DefaultFeatureConfig> MINI_DUNGEON_FEATURE;

    @Override
    public void onInitialize() {
        AutoConfig.register(CavemanConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(CavemanConfig.class).getConfig();

        LEVEL_GEN_TYPE = LevelGeneratorTypeAccessor.create(9, GENERATOR_NAME);
        CHUNK_GEN_TYPE = FabricChunkGeneratorType.register(
                GENERATOR_ID,
                CavemanChunkGenerator::new,
                ChunkGeneratorConfig::new,
                false);
        ROOM_COUNTER = ComponentRegistry.INSTANCE
                .registerIfAbsent(RoomCounterComponent.ID, IntComponent.class)
                .attach(WorldComponentCallback.EVENT, RoomCounterComponent::new);

        CommandRegistry.INSTANCE.register(false, TimedSpawnerCommand::register);

        MINI_DUNGEON_PIECE = Registry.register(
            Registry.STRUCTURE_PIECE,
            new Identifier(MODID, "mini_dungeon"),
            MiniDungeonGenerator.Piece::new
        );
        MINI_DUNGEON_FEATURE = Registry.register(
            Registry.STRUCTURE_FEATURE,
            new Identifier(MODID, "mini_dungeon"),
            new MiniDungeonFeature(DefaultFeatureConfig::deserialize)
        );
    }

    static {
        GENERATOR_NAME = "caveman";
        GENERATOR_ID = new Identifier(MODID, GENERATOR_NAME);
    }
}