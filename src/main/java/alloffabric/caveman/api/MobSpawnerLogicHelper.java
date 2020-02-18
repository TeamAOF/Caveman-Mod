package alloffabric.caveman.api;

public interface MobSpawnerLogicHelper {
    void incrementSpawnedEntities();
    void setSpawnedEntities(int number);
    int getSpawnedEntities();
    boolean hadEntityLimitPassed();
}
