package alloffabric.caveman.mixin;

import alloffabric.caveman.Caveman;
import net.minecraft.world.level.LevelGeneratorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelGeneratorType.class)
public class LevelGeneratorTypeMixin {
    @Inject(at = @At("HEAD"), method = "getTypeFromName", cancellable = true)
    private static void getTypeFromName(String name, CallbackInfoReturnable<LevelGeneratorType> info) {
        if (name.equalsIgnoreCase(Caveman.GENERATOR_NAME)) {
            info.setReturnValue(Caveman.LEVEL_GEN_TYPE);
        }
    }
}
