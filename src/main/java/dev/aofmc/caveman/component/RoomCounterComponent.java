package dev.aofmc.caveman.component;

import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.util.sync.WorldSyncedComponent;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import dev.aofmc.caveman.Caveman;

public class RoomCounterComponent implements IntComponent, WorldSyncedComponent {
    public static final Identifier ID = new Identifier(Caveman.MOD_ID, "room_counter");

    private final World world;
    private int value = 0;

    public RoomCounterComponent(World world) {
        this.world = world;
    }

    @Override
    public String getName() {
        return ID.toString();
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    @Override
    public ComponentType<IntComponent> getComponentType() {
        return Caveman.ROOM_COUNTER;
    }
}
