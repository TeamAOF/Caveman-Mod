package dev.aofmc.caveman;

import io.github.cottonmc.config.annotations.ConfigFile;

@ConfigFile(name = Caveman.MOD_ID)
public class CavemanConfig {
    public PlayerRooms playerRooms = new PlayerRooms();
    public TimedSpawners timedSpawners = new TimedSpawners();
    public MiniDungeons miniDungeons = new MiniDungeons();

    public static class PlayerRooms {
        public int spacing = 1000;
        public String structure = "caveman:spawn_room";
    }

    public static class TimedSpawners {
        public boolean defaultBehavior = false;
        public int defaultEntitiesLimit = 100;
        public String defaultLootTable = "caveman:spawner";
    }

    public static class MiniDungeons {
        public String[] structures = new String[] { "caveman:mini_dungeons/dual_spawner_chamber" };
        public double commonness = 0.25;
    }
}
