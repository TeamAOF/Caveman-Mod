package alloffabric.caveman.command;

import alloffabric.caveman.impl.SpawnerLogicHelper;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
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
                    .then(argument("enabled", bool())
                        .executes(c -> {
                            SpawnerLogicHelper helper = getHelper(c);
                            if (helper != null) {
                                helper.setBehaviorEnabled(getBool(c, "enabled"));
                                c.getSource().sendFeedback(new LiteralText("Set behavior"), false);
                                return 1;
                            }
                            c.getSource().sendFeedback(new LiteralText("Couldn't set behavior"), false);
                            return 0;
                        })
                    )
                )
                .then(literal("entities-limit")
                    .then(argument("limit", integer())
                        .executes(c -> {
                            SpawnerLogicHelper helper = getHelper(c);
                            if (helper != null) {
                                helper.setEntitiesLimit(getInteger(c, "limit"));
                                c.getSource().sendFeedback(new LiteralText("Set entities limit"), false);
                                return 1;
                            }
                            c.getSource().sendFeedback(new LiteralText("Couldn't set entities limit"), false);
                            return 0;
                        })
                    )
                )
                .then(literal("loot-table")
                    .then(argument("id", string())
                        .executes(c -> {
                            SpawnerLogicHelper helper = getHelper(c);
                            if (helper != null) {
                                helper.setLootTable(new Identifier(getString(c, "id")));
                                c.getSource().sendFeedback(new LiteralText("Set loot table"), false);
                                return 1;
                            }
                            c.getSource().sendFeedback(new LiteralText("Couldn't set loot table"), false);
                            return 0;
                        })
                    )
                )
            )
        );
    }

    private static SpawnerLogicHelper getHelper(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        BlockEntity be = context.getSource().getWorld().getBlockEntity(getBlockPos(context, "pos"));
        if (be instanceof MobSpawnerBlockEntity) {
             return (SpawnerLogicHelper) ((MobSpawnerBlockEntity) be).getLogic();
        }
        return null;
    }
}
