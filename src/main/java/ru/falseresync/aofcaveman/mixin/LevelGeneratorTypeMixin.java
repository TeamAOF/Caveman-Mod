package ru.falseresync.aofcaveman.mixin;

import net.minecraft.world.level.LevelGeneratorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.falseresync.aofcaveman.AOFCaveman;

@Mixin(LevelGeneratorType.class)
public class LevelGeneratorTypeMixin {

    @Inject(at = @At("HEAD"), method = "getTypeFromName", cancellable = true)
    private static void getTypeFromName(String name, CallbackInfoReturnable<LevelGeneratorType> info) {
        if (name.equalsIgnoreCase(AOFCaveman.GENERATOR_NAME)) {
            info.setReturnValue(AOFCaveman.LEVEL_GEN_TYPE);
        }
    }
}
