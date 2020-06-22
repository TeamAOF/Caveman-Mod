package dev.aofmc.caveman.impl;

import net.minecraft.util.Identifier;

public interface SpawnerLogicHelper {
    void setBehaviorEnabled(boolean enabled);
    void setEntitiesLimit(int limit);
    void setLootTable(Identifier id);
}
