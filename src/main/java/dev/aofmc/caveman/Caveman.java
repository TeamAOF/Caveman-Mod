package dev.aofmc.caveman;

import dev.aofmc.caveman.command.TimedSpawnerCommand;
import dev.aofmc.caveman.component.IntComponent;
import dev.aofmc.caveman.component.RoomCounterComponent;
import dev.aofmc.caveman.mixin.ChunkGeneratorTypeAccessor;
import dev.aofmc.caveman.structure.MiniDungeonGenerator;
import dev.aofmc.caveman.world.biome.StonelandBiome;
import dev.aofmc.caveman.world.biome.StonelandDeadForestBiome;
import dev.aofmc.caveman.world.chunk.CavemanBiomeSource;
import dev.aofmc.caveman.world.chunk.CavemanChunkGenerator;
import dev.aofmc.caveman.world.decorator.CountFloorDecorator;
import dev.aofmc.caveman.world.feature.MacroDungeonFeature;
import dev.aofmc.caveman.world.feature.MiniDungeonFeature;
import io.github.cottonmc.config.ConfigManager;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.event.WorldComponentCallback;
import net.earthcomputer.libstructure.LibStructure;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biomes.v1.FabricBiomes;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.block.Blocks;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.*;
import net.minecraft.world.gen.decorator.CountDecoratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import robosky.structurehelpers.structure.piece.ExtendedStructurePiece;

import java.util.Optional;

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
        MINI_DUNGEON_FEATURE = new MiniDungeonFeature(DefaultFeatureConfig.CODEC);
        LibStructure.registerStructure(
            id("mini_dungeon"),
            MINI_DUNGEON_FEATURE,
            GenerationStep.Feature.SURFACE_STRUCTURES,
            new StructureConfig(12, 8, 12345),
            null
        );

        MACRO_DUNGEON_PIECE = Registry.register(
            Registry.STRUCTURE_PIECE,
            new Identifier(MOD_ID, "macro_dungeon"),
            ExtendedStructurePiece.newFactory()
        );
        MACRO_DUNGEON_FEATURE = new MacroDungeonFeature(DefaultFeatureConfig.CODEC);
        LibStructure.registerStructure(
            id("macro_dungeon"),
            MACRO_DUNGEON_FEATURE,
            GenerationStep.Feature.SURFACE_STRUCTURES,
            new StructureConfig(12, 8, 12345),
            null
        );

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
            CavemanChunkGenerator.CODEC
        );

        CHUNK_GEN_PRESET = new ChunkGeneratorType.Preset(
            "caveman:caveman",
            preset -> ChunkGeneratorTypeAccessor.init(
                new StructuresConfig(true),
                new NoiseConfig(
                    128,
                    new NoiseSamplingConfig(1.0D, 3.0D, 80.0D, 60.0D),
                    new SlideConfig(120, 3, 0),
                    new SlideConfig(320, 4, -1),
                    1,
                    2,
                    0.0D,
                    0.019921875D,
                    false,
                    false,
                    false,
                    false
                ),
                Blocks.STONE.getDefaultState(),
                Blocks.WATER.getDefaultState(),
                255,
                0,
                32,
                false,
                Optional.of(preset)
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
