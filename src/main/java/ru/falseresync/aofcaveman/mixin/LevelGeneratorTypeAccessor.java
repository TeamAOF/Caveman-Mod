package ru.falseresync.aofcaveman.worldgen.mixin;

import net.minecraft.world.level.LevelGeneratorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * @author Valoeghese in ClimaticWorldType
 */
@Mixin(LevelGeneratorType.class)
public interface LevelGeneratorTypeAccessor {
    @Invoker("<init>")
    static LevelGeneratorType create(int id, String name) {
        throw new AssertionError("f");
    }
}
