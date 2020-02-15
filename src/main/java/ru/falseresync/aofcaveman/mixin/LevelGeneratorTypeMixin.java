package ru.falseresync.aofcaveman.worldgen.mixin;

import net.minecraft.world.level.LevelGeneratorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.falseresync.aofcaveman.worldgen.AofCavemanWorldGen;

/**
 * @author Valoeghese in ClimaticWorldType
 */
@Mixin(LevelGeneratorType.class)
public class LevelGeneratorTypeMixin {

    @Inject(at = @At("HEAD"), method = "getTypeFromName", cancellable = true)
    private static void getTypeFromName(String name, CallbackInfoReturnable<LevelGeneratorType> info) {
        if (name.equalsIgnoreCase("aofcaveman")) {
            info.setReturnValue(AofCavemanWorldGen.LEVEL_GEN_TYPE);
        }
    }
}
