package ru.falseresync.aofcaveman.worldgen.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.falseresync.aofcaveman.worldgen.AofCavemanWorldGen;

import java.util.Random;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Inject(method = "onPlayerConnect", at = @At("TAIL"))
    public void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo info) {
        if (player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.LEAVE_GAME)) == 0) {
            ServerWorld world = player.getServerWorld();
            LevelProperties levelProperties = world.getLevelProperties();
            if (levelProperties.getGeneratorType() != AofCavemanWorldGen.LEVEL_GEN_TYPE) return;

            StructureManager manager = world.getStructureManager();
            Structure structure = manager.getStructureOrBlank(AofCavemanWorldGen.SPAWN_ROOM_ID);

            Random random = new Random();
            int max = (int) ((levelProperties.getBorderSize() / 2) * 0.9);
            int x = (random.nextInt(2) == 1 ? -1 : 1) * random.nextInt(max);
            int y = 50 + random.nextInt(21);
            int z = (random.nextInt(2) == 1 ? -1 : 1) * random.nextInt(max);
            BlockPos roomPos = new BlockPos(x, y, z);
            BlockPos spawnPos = roomPos.add(structure.getSize().getX() / 2, 1, structure.getSize().getZ() / 2);

            structure.place(world, roomPos, new StructurePlacementData());
            world.getChunkManager().addTicket(
                    ChunkTicketType.POST_TELEPORT,
                    new ChunkPos(spawnPos),
                    1, // => radius = 33 - it
                    player.getEntityId()
            );
            player.setPlayerSpawn(spawnPos, true, false);
            player.networkHandler.requestTeleport(
                    spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, player.yaw, player.pitch);
        }
    }
}
