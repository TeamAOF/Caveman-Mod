package alloffabric.caveman.command;

import alloffabric.caveman.impl.SpawnerLogicHelper;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

import static com.mojang.brigadier.arguments.BoolArgumentType.bool;
import static com.mojang.brigadier.arguments.BoolArgumentType.getBool;
import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static net.minecraft.command.arguments.BlockPosArgumentType.blockPos;
import static net.minecraft.command.arguments.BlockPosArgumentType.getBlockPos;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class TimedSpawnerCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("timed-spawner")
                .requires(source -> source.hasPermissionLevel(3))
                .then(argument("pos", blockPos())
                        .then(literal("behavior-enabled")
                                .then(argument("enabled", bool()))
                                .executes(c -> {
                                    BlockEntity be = c.getSource().getWorld().getBlockEntity(getBlockPos(c, "pos"));
                                    if (be instanceof MobSpawnerBlockEntity) {
                                        ((SpawnerLogicHelper) be).setBehaviorEnabled(getBool(c, "enabled"));
                                    }
                                    return 1;
                                })
                        )
                        .then(literal("entities-limit")
                                .then(argument("limit", integer()))
                                .executes(c -> {
                                    BlockEntity be = c.getSource().getWorld().getBlockEntity(getBlockPos(c, "pos"));
                                    if (be instanceof MobSpawnerBlockEntity) {
                                        ((SpawnerLogicHelper) be).setEntitiesLimit(getInteger(c, "limit"));
                                    }
                                    return 1;
                                })
                        )
                        .then(literal("loot-table")
                                .then(argument("id", string()))
                                .executes(c -> {
                                    BlockEntity be = c.getSource().getWorld().getBlockEntity(getBlockPos(c, "pos"));
                                    if (be instanceof MobSpawnerBlockEntity) {
                                        ((SpawnerLogicHelper) be).setLootTable(new Identifier(getString(c, "id")));
                                    }
                                    return 1;
                                })
                        )
                )
        );
    }
}
