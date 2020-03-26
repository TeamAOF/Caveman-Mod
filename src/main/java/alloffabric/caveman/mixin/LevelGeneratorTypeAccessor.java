package alloffabric.caveman.mixin;

import com.mojang.datafixers.Dynamic;
import net.minecraft.world.level.LevelGeneratorOptions;
import net.minecraft.world.level.LevelGeneratorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.BiFunction;

@Mixin(LevelGeneratorType.class)
public interface LevelGeneratorTypeAccessor {
    @Invoker("<init>")
    static LevelGeneratorType create(int id, String name, BiFunction<LevelGeneratorType, Dynamic<?>, LevelGeneratorOptions> optionsFactory) {
        throw new AssertionError("f");
    }
}
