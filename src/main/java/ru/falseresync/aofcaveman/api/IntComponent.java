package ru.falseresync.aofcaveman.api;

import nerdhub.cardinal.components.api.component.Component;
import net.minecraft.nbt.CompoundTag;

public interface IntComponent extends Component {
    String getName();

    int getValue();

    void setValue(int value);

    default void increment() {
        this.setValue(this.getValue() + 1);
    }

    default void decrement() {
        this.setValue(this.getValue() - 1);
    }

    @Override
    default void fromTag(CompoundTag tag) {
        this.setValue(tag.getInt(this.getName()));
    }

    @Override
    default CompoundTag toTag(CompoundTag tag) {
        tag.putInt(this.getName(), this.getValue());
        return tag;
    }
}
