package dev.aofmc.caveman.mixin;

import dev.aofmc.caveman.Caveman;
import dev.aofmc.caveman.world.PlayerRoom;
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

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Inject(method = "onPlayerConnect", at = @At("TAIL"))
    private void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo info) {
        if (player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.LEAVE_GAME)) == 0) {
            ServerWorld world = player.getServerWorld();
            LevelProperties levelProperties = (LevelProperties) world.getLevelProperties();
            if (levelProperties.getGeneratorType() != Caveman.LEVEL_GEN_TYPE) return;

            PlayerRoom.create(player, world);
        }
    }
}
