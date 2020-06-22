package dev.aofmc.caveman.mixin;

import dev.aofmc.caveman.Caveman;
import dev.aofmc.caveman.impl.SpawnerLogicHelper;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.MobSpawnerLogic;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(MobSpawnerLogic.class)
public class MobSpawnerLogicMixin implements SpawnerLogicHelper {
    // How to clean those up?
    // Perfectly they would only be the declarations,
    // while the initialisation will happen somewhere else
    // like in a constructor
    private boolean behaviorEnabled = Caveman.CONFIG.timedSpawners.defaultBehavior;
    private int entitiesLimit = Caveman.CONFIG.timedSpawners.defaultEntitiesLimit;
    private Identifier lootTable = new Identifier(Caveman.CONFIG.timedSpawners.defaultLootTable);
    private int spawnedEntities = 0;

    @Inject(method = "spawnEntity", at = @At("RETURN"))
    private void spawnEntity(Entity entity, CallbackInfo info) {
        if (this.behaviorEnabled) {
            MobSpawnerLogic logic = (MobSpawnerLogic) (Object) this;
            World world = logic.getWorld();
            BlockPos pos = logic.getPos();
            this.spawnedEntities += 1;
            if (this.spawnedEntities > this.entitiesLimit) {
                world.setBlockState(pos, Blocks.CHEST.getDefaultState());
                BlockEntity chest = world.getBlockEntity(pos);
                if (chest instanceof ChestBlockEntity) {
                    ((ChestBlockEntity) chest).setLootTable(this.lootTable, new Random().nextLong());
                    world.updateNeighbors(pos, world.getBlockState(pos).getBlock());
                }
            }
        }
    }

    @Inject(method = "fromTag", at = @At("RETURN"))
    private void deserialize(CompoundTag outerTag, CallbackInfo info) {
        // Type 10 for CompoundTag
        if (outerTag.contains(Caveman.MOD_ID, 10)) {
            CompoundTag tag = outerTag.getCompound(Caveman.MOD_ID);
            this.behaviorEnabled = tag.getBoolean("behaviorEnabled");
            if (this.behaviorEnabled) {
                this.entitiesLimit = tag.getInt("entitiesLimit");
                this.lootTable = new Identifier(tag.getString("lootTable"));
                this.spawnedEntities = tag.getInt("spawnedEntities");
            }
        }
    }

    @Inject(method = "toTag", at = @At("RETURN"))
    private void serialize(CompoundTag outerTag, CallbackInfoReturnable<CompoundTag> info) {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("behaviorEnabled", this.behaviorEnabled);
        if (this.behaviorEnabled) {
             tag.putInt("entitiesLimit", this.entitiesLimit);
             tag.putString("lootTable", this.lootTable.toString());
             tag.putInt("spawnedEntities", this.spawnedEntities);
        }
        outerTag.put(Caveman.MOD_ID, tag);
    }

    @Override
    public void setBehaviorEnabled(boolean enabled) {
        this.behaviorEnabled = enabled;
    }

    @Override
    public void setEntitiesLimit(int limit) {
        this.entitiesLimit = limit;
    }

    @Override
    public void setLootTable(Identifier id) {
        this.lootTable = id;
    }
}
