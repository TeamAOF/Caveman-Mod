package dev.aofmc.caveman;

import dev.aofmc.caveman.world.CavemanGeneratorType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class CavemanClient implements ClientModInitializer {
    public static CavemanGeneratorType GEN_TYPE;

    @Override
    public void onInitializeClient() {
        GEN_TYPE = new CavemanGeneratorType();
    }
}
