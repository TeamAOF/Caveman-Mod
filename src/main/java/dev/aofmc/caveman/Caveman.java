package dev.aofmc.caveman;

import dev.aofmc.caveman.command.TimedSpawnerCommand;
import dev.aofmc.caveman.component.IntComponent;
import dev.aofmc.caveman.component.RoomCounterComponent;
import dev.aofmc.caveman.structure.MiniDungeonGenerator;
import dev.aofmc.caveman.world.biome.StonelandBiome;
import dev.aofmc.caveman.world.biome.StonelandDeadForestBiome;
import dev.aofmc.caveman.world.chunk.CavemanBiomeSource;
import dev.aofmc.caveman.world.decorator.CountFloorDecorator;
import dev.aofmc.caveman.world.feature.MacroDungeonFeature;
import dev.aofmc.caveman.world.feature.MiniDungeonFeature;
import io.github.cottonmc.config.ConfigManager;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.event.WorldComponentCallback;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biomes.v1.FabricBiomes;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.gen.decorator.CountDecoratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import robosky.structurehelpers.structure.piece.ExtendedStructurePiece;

import static dev.aofmc.caveman.util.IdentifierUtil.id;

public class Caveman implements ModInitializer {
    public static final String MOD_ID = "caveman";
    public static final String GENERATOR_NAME;
    public static final Identifier GENERATOR_ID;
    public static ChunkGeneratorType.Preset CHUNK_GEN_PRESET;
    public static ComponentType<IntComponent> ROOM_COUNTER;
    public static CavemanConfig CONFIG;
    public static StructurePieceType MINI_DUNGEON_PIECE;
    public static StructureFeature<DefaultFeatureConfig> MINI_DUNGEON_FEATURE;
    public static ExtendedStructurePiece.Factory MACRO_DUNGEON_PIECE;
    public static StructureFeature<DefaultFeatureConfig> MACRO_DUNGEON_FEATURE;
    public static StonelandBiome STONELAND_BIOME;
    public static StonelandDeadForestBiome STONELAND_FOREST_BIOME;
    public static CountFloorDecorator COUNT_FLOOR_DECORATOR;

    static {
        GENERATOR_NAME = "caveman";
        GENERATOR_ID = new Identifier(MOD_ID, GENERATOR_NAME);
    }

    @Override
    public void onInitialize() {
        CONFIG = ConfigManager.loadConfig(CavemanConfig.class);

        MINI_DUNGEON_PIECE = Registry.register(
            Registry.STRUCTURE_PIECE,
            new Identifier(MOD_ID, "mini_dungeon"),
            MiniDungeonGenerator.Piece::new
        );
        MINI_DUNGEON_FEATURE = Registry.register(
            Registry.STRUCTURE_FEATURE,
            new Identifier(MOD_ID, "mini_dungeon"),
            new MiniDungeonFeature(DefaultFeatureConfig.CODEC)
        );

        MACRO_DUNGEON_PIECE = Registry.register(
            Registry.STRUCTURE_PIECE,
            new Identifier(MOD_ID, "macro_dungeon"),
            ExtendedStructurePiece.newFactory()
        );
        MACRO_DUNGEON_FEATURE = Registry.register(
            Registry.STRUCTURE_FEATURE,
            new Identifier(MOD_ID, "macro_dungeon"),
            new MacroDungeonFeature(DefaultFeatureConfig.CODEC)
        );
        StructureFeature.STRUCTURES.put(MOD_ID + ":macro_dungeon", MACRO_DUNGEON_FEATURE);

        COUNT_FLOOR_DECORATOR = Registry.register(
            Registry.DECORATOR,
            new Identifier(MOD_ID, "count_floor"),
            new CountFloorDecorator(CountDecoratorConfig.field_24985)
        );

        STONELAND_BIOME = Registry.register(
            Registry.BIOME,
            new Identifier(MOD_ID, "stoneland"),
            new StonelandBiome()
        );
        FabricBiomes.addSpawnBiome(STONELAND_BIOME);

        STONELAND_FOREST_BIOME = Registry.register(
            Registry.BIOME,
            new Identifier(MOD_ID, "stoneland_dead_forest"),
            new StonelandDeadForestBiome()
        );

        Registry.register(
            Registry.BIOME_SOURCE,
            id("caveman"),
            CavemanBiomeSource.CODEC
        );

        Registry.register(
            Registry.CHUNK_GENERATOR,
            id("caveman"),
            // CODEC here
        );

        CHUNK_GEN_PRESET = new ChunkGeneratorType.Preset(
            "caveman:caveman",
            preset -> new ChunkGeneratorType(
                // something??? here
            )
        );


        ROOM_COUNTER = ComponentRegistry.INSTANCE
            .registerIfAbsent(RoomCounterComponent.ID, IntComponent.class)
            .attach(WorldComponentCallback.EVENT, RoomCounterComponent::new);

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            if (!dedicated) {
                TimedSpawnerCommand.register(dispatcher);
            }
        });
    }
}
