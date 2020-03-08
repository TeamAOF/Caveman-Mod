package alloffabric.caveman;

import alloffabric.caveman.component.IntComponent;
import alloffabric.caveman.command.TimedSpawnerCommand;
import alloffabric.caveman.component.RoomCounterComponent;
import alloffabric.caveman.mixin.LevelGeneratorTypeAccessor;
import alloffabric.caveman.structure.MacroDungeonGenerator;
import alloffabric.caveman.structure.MiniDungeonGenerator;
import alloffabric.caveman.structure.processor.NoIgnoreAirProcessor;
import alloffabric.caveman.world.biome.StonelandBiome;
import alloffabric.caveman.world.chunk.CavemanChunkGenerator;
import alloffabric.caveman.world.chunk.CavemanChunkGeneratorConfig;
import alloffabric.caveman.world.chunk.FabricChunkGeneratorType;
import alloffabric.caveman.world.feature.MacroDungeonFeature;
import alloffabric.caveman.world.feature.MiniDungeonFeature;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.event.WorldComponentCallback;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biomes.v1.FabricBiomes;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.level.LevelGeneratorType;


public class Caveman implements ModInitializer {
    public static final String MOD_ID = "caveman";
    public static final String GENERATOR_NAME;
    public static final Identifier GENERATOR_ID;
    public static LevelGeneratorType LEVEL_GEN_TYPE;
    public static ChunkGeneratorType<CavemanChunkGeneratorConfig, CavemanChunkGenerator> CHUNK_GEN_TYPE;
    public static ComponentType<IntComponent> ROOM_COUNTER;
    public static CavemanConfig CONFIG;
    public static StructurePieceType MINI_DUNGEON_PIECE;
    public static StructureFeature<DefaultFeatureConfig> MINI_DUNGEON_FEATURE;
    public static StructurePieceType MACRO_DUNGEON_PIECE;
    public static StructureFeature<DefaultFeatureConfig> MACRO_DUNGEON_FEATURE;
    public static StructureProcessorType NO_IGNORE_AIR_STRUCTURE_PROCESSOR;
    public static StonelandBiome STONELAND_BIOME;

    @Override
    public void onInitialize() {
        CONFIG = ConfigManager.loadConfig(CavemanConfig.class, MOD_ID);

        LEVEL_GEN_TYPE = LevelGeneratorTypeAccessor.create(9, GENERATOR_NAME);
        CHUNK_GEN_TYPE = FabricChunkGeneratorType.register(
                GENERATOR_ID,
                CavemanChunkGenerator::new,
                CavemanChunkGeneratorConfig::new,
                false);
        ROOM_COUNTER = ComponentRegistry.INSTANCE
                .registerIfAbsent(RoomCounterComponent.ID, IntComponent.class)
                .attach(WorldComponentCallback.EVENT, RoomCounterComponent::new);

        CommandRegistry.INSTANCE.register(false, TimedSpawnerCommand::register);

        NO_IGNORE_AIR_STRUCTURE_PROCESSOR = Registry.register(
            Registry.STRUCTURE_PROCESSOR,
            new Identifier(MOD_ID, "no_ignore_air"),
            dynamic -> NoIgnoreAirProcessor.INSTANCE
        );

        MINI_DUNGEON_PIECE = Registry.register(
            Registry.STRUCTURE_PIECE,
            new Identifier(MOD_ID, "mini_dungeon"),
            MiniDungeonGenerator.Piece::new
        );
        MINI_DUNGEON_FEATURE = Registry.register(
            Registry.STRUCTURE_FEATURE,
            new Identifier(MOD_ID, "mini_dungeon"),
            new MiniDungeonFeature(DefaultFeatureConfig::deserialize)
        );
        Feature.STRUCTURES.put(MOD_ID + ":mini_dungeon", MINI_DUNGEON_FEATURE);

        MACRO_DUNGEON_PIECE = Registry.register(
            Registry.STRUCTURE_PIECE,
            new Identifier(MOD_ID, "macro_dungeon"),
            MacroDungeonGenerator.Piece::new
        );
        MACRO_DUNGEON_FEATURE = Registry.register(
            Registry.STRUCTURE_FEATURE,
            new Identifier(MOD_ID, "macro_dungeon"),
            new MacroDungeonFeature(DefaultFeatureConfig::deserialize)
        );
        Feature.STRUCTURES.put(MOD_ID + ":mini_dungeon", MACRO_DUNGEON_FEATURE);

        STONELAND_BIOME = Registry.register(
            Registry.BIOME,
            new Identifier(MOD_ID, "stoneland"),
            new StonelandBiome()
        );
        FabricBiomes.addSpawnBiome(STONELAND_BIOME);
    }

    static {
        GENERATOR_NAME = "caveman";
        GENERATOR_ID = new Identifier(MOD_ID, GENERATOR_NAME);
    }
}
