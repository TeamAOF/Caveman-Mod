package ru.falseresync.aofcaveman.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.falseresync.aofcaveman.AOFCaveman;
import ru.falseresync.aofcaveman.world.PlayerRoom;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Inject(method = "onPlayerConnect", at = @At("TAIL"))
    public void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo info) {
        if (player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.LEAVE_GAME)) == 0) {
            ServerWorld world = player.getServerWorld();
            LevelProperties levelProperties = world.getLevelProperties();
            if (levelProperties.getGeneratorType() != AOFCaveman.LEVEL_GEN_TYPE) return;

            PlayerRoom.create(player, world);
        }
    }
}
