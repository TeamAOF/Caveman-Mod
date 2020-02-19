package alloffabric.caveman;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

@Config(name = Caveman.MODID)
public class CavemanConfig implements ConfigData {
    public PlayerRooms playerRooms = new PlayerRooms();
    public TimedSpawners timedSpawners = new TimedSpawners();

    public static class PlayerRooms {
        public int spacing = 1000;
        public String structureId = "caveman:spawn_room";
    }

    public static class TimedSpawners {
        public boolean defaultBehavior = false;
        public int defaultEntitiesLimit = 100;
        public String defaultLootTable = "caveman:spawner";
    }
}
