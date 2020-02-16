package ru.falseresync.aofcaveman.world;

import nerdhub.cardinal.components.api.component.ComponentProvider;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import ru.falseresync.aofcaveman.AOFCaveman;
import ru.falseresync.aofcaveman.api.IntComponent;

public class PlayerRoom {
    public static void create(ServerPlayerEntity player, ServerWorld world) {
        IntComponent counter = ComponentProvider.fromWorld(world).getComponent(AOFCaveman.ROOM_COUNTER);
        if (counter == null) {
            throw new IllegalStateException("Given world doesn't have the room counter component attached!");
        }

        StructureManager manager = world.getStructureManager();
        Structure structure = manager.getStructureOrBlank(AOFCaveman.SPAWN_ROOM_ID);

        BlockPos roomPos = calculatePos(counter.getValue());
        BlockPos spawnPos = roomPos.add(structure.getSize().getX() / 2, 1, structure.getSize().getZ() / 2);

        structure.place(world, roomPos, new StructurePlacementData());
        world.getChunkManager().addTicket(
                ChunkTicketType.POST_TELEPORT,
                new ChunkPos(spawnPos),
                10, // => radius = 33 - it
                player.getEntityId()
        );
        player.setPlayerSpawn(spawnPos, true, false);
        player.networkHandler.requestTeleport(
                spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, player.yaw, player.pitch);

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
            counter.increment();
        }
    }

    public static BlockPos calculatePos(int roomNumber) {
        if (roomNumber == 0) {
            return new BlockPos(0, 64, 0);
        } else {
            roomNumber -= 1;

            int ringNumber = 0;
            int roomsNumberBeforeAndOnCurrentRing = 0;
            while (roomsNumberBeforeAndOnCurrentRing <= roomNumber) {
                ringNumber += 1;
                roomsNumberBeforeAndOnCurrentRing += 8 * ringNumber;
            }

            int roomNumberOnCurrentRing = roomNumber;
            for (int i = 0; i < ringNumber; i++) {
                roomNumberOnCurrentRing -= 8 * i;
            }

            int ringSideSize = 2 * ringNumber;
            int ringSideNumber = roomNumberOnCurrentRing / ringSideSize % 4;
            int ringSideStartingRoomNumber = roomsNumberBeforeAndOnCurrentRing - ringSideSize * (4 - ringSideNumber);

            int x = 0;
            int z = 0;
            int diagonalDelta = ringNumber - 1;
            int orthogonalDelta = roomNumber - ringSideStartingRoomNumber;

            switch (ringSideNumber) {
                case 0:
                    x = 1 + diagonalDelta;
                    z = 0 - diagonalDelta + orthogonalDelta;
                    break;
                case 1:
                    x = 0 + diagonalDelta - orthogonalDelta;
                    z = 1 + diagonalDelta;
                    break;
                case 2:
                    x = -1 - diagonalDelta;
                    z = 0 + diagonalDelta - orthogonalDelta;
                    break;
                case 3:
                    x = 0 - diagonalDelta + orthogonalDelta;
                    z = -1 - diagonalDelta;
                    break;
            }

            return new BlockPos(x * 1000, 64, z * 1000);
        }
    }
}
