package alloffabric.caveman;

public class CavemanConfig {
    public VanillaTweaks vanillaTweaks = new VanillaTweaks();
    public PlayerRooms playerRooms = new PlayerRooms();
    public TimedSpawners timedSpawners = new TimedSpawners();
    public MiniDungeons miniDungeons = new MiniDungeons();

    public static class VanillaTweaks {
        public double mineshaftsCommonness = 0.25;
    }

    public static class PlayerRooms {
        public int spacing = 1000;
        public String structureId = "caveman:spawn_room";
    }

    public static class TimedSpawners {
        public boolean defaultBehavior = false;
        public int defaultEntitiesLimit = 100;
        public String defaultLootTable = "caveman:spawner";
    }

    public static class MiniDungeons {
        public String[] structures = new String[] { "caveman:mini_dungeon_0" };
        public double commonness = 0.25;
    }
}
